package se.braindome.skywatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.braindome.skywatch.network.RetrofitInstance
import se.braindome.skywatch.ui.home.HomeScreen
import se.braindome.skywatch.ui.theme.SkywatchTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        HomeScreen(innerPadding, { fetchWeather() })

                    }

                }
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
                    apiKey = key
                )
                Timber.d("Forecast: $response")
            } catch (e: Exception) {
                Timber.e(e,"Failed to fetch forecast")
            }
        }
    }
}

