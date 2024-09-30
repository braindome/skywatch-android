package se.braindome.skywatch.ui.home

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.braindome.skywatch.BuildConfig
import se.braindome.skywatch.network.ForecastResponse
import se.braindome.skywatch.network.RetrofitInstance
import se.braindome.skywatch.ui.utils.Units
import timber.log.Timber

data class HomeUiState(
    val forecastResponse: ForecastResponse?,
    val loading: Boolean = false,
)

class HomeViewModel : ViewModel() {

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
}