package se.braindome.skywatch.ui.home

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.braindome.skywatch.BuildConfig
import se.braindome.skywatch.LOCATION_PERMISSION_REQUEST_CODE
import se.braindome.skywatch.MainActivity
import se.braindome.skywatch.location.LocationRepository
import se.braindome.skywatch.network.ForecastResponse
import se.braindome.skywatch.network.RetrofitInstance
import se.braindome.skywatch.ui.utils.Units
import timber.log.Timber
import javax.inject.Inject

data class HomeUiState(
    val forecastResponse: ForecastResponse?,
    val loading: Boolean = false,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(null, true))
    var uiState = _uiState.asStateFlow()

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        val key = BuildConfig.OPEN_WEATHER_MAP_API_KEY
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getForecast(
                    lat = 57.70,
                    lon = 11.96,
                    exclude = "minutely",
                    apiKey = key,
                    units = Units.METRIC
                )
                Timber.d("Forecast: ${response.current.temp}")
                _uiState.value = HomeUiState(response, false)
            } catch (e: Exception) {
                Timber.e(e,"Failed to fetch forecast")
                _uiState.value = HomeUiState(null, false)
            }
        }
    }

    fun updateLocation(activity: MainActivity) {
        locationRepository.getLastLocation(activity) { latitude, longitude ->
            Timber.d("Location: $latitude, $longitude")

        }
    }

    /*

    fun getLocation(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val location = locationClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                Timber.d("Location: ${it.latitude}, ${it.longitude}")
            }
        }
    }

     */
}