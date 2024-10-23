package se.braindome.skywatch

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.braindome.skywatch.location.LocationRepository
import se.braindome.skywatch.network.mockForecastResponse
import se.braindome.skywatch.notification.NotificationHelper
import se.braindome.skywatch.ui.home.HomeScreen
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.home.HomeViewModel
import se.braindome.skywatch.ui.home.cards.MainInfoCard
import se.braindome.skywatch.ui.home.daily.DailyRow
import se.braindome.skywatch.ui.home.hourly.HourlyColumn
import se.braindome.skywatch.ui.theme.SkywatchTheme
import timber.log.Timber
import javax.inject.Inject

const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var locationRepository: LocationRepository
    @Inject lateinit var notificationHelper: NotificationHelper

    private lateinit var viewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            lifecycleScope.launch {
                delay(1000) // Add a delay to ensure permission is granted
                updateLocation()
            }
        } else {
            Timber.e("Location permission denied")
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        notificationHelper = NotificationHelper(this)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Creating the notification channel
        notificationHelper.createNotificationChannel()

        // Collecting weather state changes
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.forecastResponse != null) {
                    val temp = state.forecastResponse.current.temp
                    val condition = state.forecastResponse.current.weather[0].description
                    notificationHelper.sendNotification(
                        title = "Weather Update",
                        content = "The temperature is $temp and the condition is $condition",
                    )
                }
            }
        }

        setContent {
            SkywatchTheme {
                val uiState = viewModel.uiState.collectAsState()

                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .background(Color.Black)
                ) {
                    Scaffold(
                        modifier = Modifier.background(Color.Black).padding(horizontal = 8.dp)
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier.fillMaxSize().background(Color.Black).padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HomeScreen(
                                onClick = {
                                    checkLocationPermission()
                                },
                                viewModel = viewModel
                            )

                        }

                    }
                }
                LoadingOverlay(isLoading = uiState.value.loading)
            }
        }

        checkLocationPermission()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
            window.decorView.setBackgroundColor(Color.Black.toArgb())
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        } else {
            updateLocation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateLocation() {
        viewModel.updateLocation(this)
    }
}

@Composable
fun LoadingOverlay(isLoading: Boolean) {
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
