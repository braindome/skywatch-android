package se.braindome.skywatch.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.braindome.skywatch.BuildConfig
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
    val location: String? = "Unknown location"
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(null, true))
    var uiState = _uiState.asStateFlow()

    init {
        //fetchWeather()
        //updateLocation(MainActivity())
    }

    private fun fetchWeather(lat: Double, lon: Double, location: String) {
        val key = BuildConfig.OPEN_WEATHER_MAP_API_KEY
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getForecast(
                    lat = lat,
                    lon = lon,
                    exclude = null,
                    apiKey = key,
                    units = Units.METRIC
                )
                val geocodingResponse = RetrofitInstance.api.getLocationName(
                    lat = lat,
                    lon = lon,
                    limit = 1,
                    apiKey = key
                )
                Timber.tag("ApiGeocoder").d("Forecast: ${response.current.temp}")
                Timber.tag("ApiGeocoder").d("Geocoding response: ${geocodingResponse[0].name}.")
                _uiState.value = HomeUiState(response, false, geocodingResponse[0].name)
            } catch (e: Exception) {
                Timber.e(e,"Failed to fetch forecast")
                _uiState.value = HomeUiState(null, false)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun updateLocation(activity: MainActivity) {
        _uiState.value = HomeUiState(null, true)
        locationRepository.getLastLocation(activity) { latitude, longitude, locationName ->
            Timber.d("Location: $latitude, $longitude, $locationName")
            fetchWeather(latitude, longitude, locationName)
        }
    }
}