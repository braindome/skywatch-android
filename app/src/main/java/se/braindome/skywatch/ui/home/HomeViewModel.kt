package se.braindome.skywatch.ui.home

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import se.braindome.skywatch.network.ForecastResponse

data class HomeUiState(
    val forecastResponse: ForecastResponse?,
    val loading: Boolean = false,
)

class HomeViewModel : ViewModel() {

}