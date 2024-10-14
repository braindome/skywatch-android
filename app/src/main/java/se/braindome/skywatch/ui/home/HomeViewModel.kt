package se.braindome.skywatch.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val key = BuildConfig.OPEN_WEATHER_MAP_API_KEY
    private val _uiState = MutableStateFlow(HomeUiState(null, true))
    var uiState = _uiState.asStateFlow()

    init {
        //fetchWeather()
        //updateLocation(MainActivity())
    }

    private fun fetchWeather(lat: Double, lon: Double, location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val forecastDeferred = async { fetchForecast(lat, lon) }
                val locationNameDeferred = async { fetchLocationName(lat, lon) }
                val forecastResponse = forecastDeferred.await()
                val locationName = locationNameDeferred.await()

                withContext(Dispatchers.Main) {
                    Timber.tag("ApiGeocoder").d("Forecast: ${forecastResponse.current.temp}")
                    Timber.tag("ApiGeocoder").d("Geocoding response: ${locationName}.")
                    _uiState.value = HomeUiState(forecastResponse, false, locationName)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Timber.e(e,"Failed to fetch forecast")
                    _uiState.value = HomeUiState(null, false)
                }
            }
        }
    }

    private suspend fun fetchForecast(lat: Double, lon: Double): ForecastResponse {
        return RetrofitInstance.api.getForecast(
            lat = lat,
            lon = lon,
            exclude = null,
            apiKey = key,
            units = Units.METRIC
        )
    }

    private suspend fun fetchLocationName(lat: Double, lon: Double): String {
        val geocodingResponse = RetrofitInstance.api.getLocationName(
            lat = lat,
            lon = lon,
            limit = 1,
            apiKey = key
        )
        return geocodingResponse[0].name
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