package se.braindome.skywatch

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.braindome.skywatch.location.LocationRepository
import se.braindome.skywatch.network.RetrofitInstance
import se.braindome.skywatch.notification.NotificationHelper
import se.braindome.skywatch.ui.home.HomeScreen
import se.braindome.skywatch.ui.home.HomeViewModel
import se.braindome.skywatch.ui.theme.SkywatchTheme
import timber.log.Timber
import javax.inject.Inject

const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var locationRepository: LocationRepository
    @Inject lateinit var notificationHelper: NotificationHelper

    private lateinit var viewModel: HomeViewModel

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


    override fun onCreate(savedInstanceState: Bundle?) {
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


        enableEdgeToEdge()
        setContent {
            SkywatchTheme {
                Scaffold(

                ) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HomeScreen(
                            padding = innerPadding,
                            onClick = {
                               checkLocationPermission()
                            },
                            viewModel = viewModel
                        )

                    }

                }
            }
        }

        checkLocationPermission()
    }

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

    private fun updateLocation() {
        viewModel.updateLocation(this)
    }
}

