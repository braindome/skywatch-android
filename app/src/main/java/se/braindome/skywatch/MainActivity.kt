package se.braindome.skywatch

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.braindome.skywatch.network.RetrofitInstance
import se.braindome.skywatch.ui.home.HomeScreen
import se.braindome.skywatch.ui.home.HomeViewModel
import se.braindome.skywatch.ui.theme.SkywatchTheme
import timber.log.Timber

private const val LOCATION_PERMISSION_REQUEST_CODE = 1

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel : HomeViewModel = HomeViewModel()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
                                fetchWeather()
                                getLocation()
                            },
                            viewModel = viewModel
                        )

                    }

                }
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val location = fusedLocationClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                Timber.d("Location: ${it.latitude}, ${it.longitude}")
            }
        }
    }

    private fun fetchWeather() {
        val key = BuildConfig.OPEN_WEATHER_MAP_API_KEY
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getForecast(
                    lat = 33.44,
                    lon = 94.04,
                    exclude = "minutely",
                    apiKey = key,
                    units = "metric"
                )
                Timber.d("Forecast: ${response.current.temp}")
            } catch (e: Exception) {
                Timber.e(e,"Failed to fetch forecast")
            }
        }
    }
}

