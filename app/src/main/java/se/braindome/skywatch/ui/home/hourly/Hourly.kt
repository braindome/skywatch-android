package se.braindome.skywatch.ui.home.hourly

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.braindome.skywatch.R
import se.braindome.skywatch.location.LocationRepository
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.home.HomeViewModel
import se.braindome.skywatch.ui.theme.getBackgroundColor
import se.braindome.skywatch.ui.utils.DateTimeUtils
import se.braindome.skywatch.ui.utils.IconResourceProvider
import timber.log.Timber
import kotlin.collections.get

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyColumn(weatherState: State<HomeUiState>) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Icon(
                painter = painterResource(
                    id = if (isExpanded) R.drawable.baseline_expand_less_24 else R.drawable.baseline_expand_more_24
                ),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
                    .size(34.dp).clickable { isExpanded = !isExpanded },
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Next 24 Hours",
                style = MaterialTheme.typography.titleLarge,
            )

        }
        if (isExpanded) {
            LazyColumn(
                modifier = Modifier.padding(2.dp)
            ) {
                items(24) { index -> HourlyItem(weatherState, index) }
            }
        }

    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyItem(weatherState: State<HomeUiState>, index: Int) {
    val hourlyState = weatherState.value.forecastResponse?.hourly?.get(index)
    val weatherIcon = hourlyState?.weather?.get(0)?.icon
    val sunset = weatherState.value.forecastResponse?.daily?.get(0)?.sunset
    val sunrise = weatherState.value.forecastResponse?.daily?.get(0)?.sunrise
    val localTime = DateTimeUtils.convertToLocalTime(hourlyState?.dt ?: 0, true)

    // Log raw data
    Timber.tag("RawData").d("Raw Sunrise: $sunrise, Raw Sunset: $sunset")

    // Convert to local time
    val localSunrise = DateTimeUtils.convertToLocalTime(sunrise ?: 0, format24 = true)
    val localSunset = DateTimeUtils.convertToLocalTime(sunset ?: 0, format24 = true)

    // Log converted times
    //Timber.tag("ConvertedTimes").d("Converted Sunrise: $localSunrise, Converted Sunset: $localSunset")
    val backgroundColor = getBackgroundColor(
        localTimeString = localTime,
        sunriseString = localSunrise,
        sunsetString = localSunset,
        weatherCondition = hourlyState?.weather?.get(0)?.description
    )


    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = IconResourceProvider.assignIcon(weatherIcon.toString())),
            contentDescription = "weather icon",
            tint = Color.White,
            modifier = Modifier.size(42.dp).padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = DateTimeUtils.convertToLocalTime(
                    dt = hourlyState?.dt ?: 0,
                    format24 = true
                ),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
            Text(
                text = hourlyState?.weather?.get(0)?.description.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = hourlyState?.temp?.toInt().toString() + " \u00B0C",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
            Text(
                text = "Feels like " + hourlyState?.feels_like?.toInt().toString() + " \u00B0C",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HourlyItemPreview() {
    val vm = HomeViewModel(locationRepository = LocationRepository(LocalContext.current))
    val state = vm.uiState.collectAsState()
    HourlyItem(weatherState = state, index = 0)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HourlyColumnPreview() {
    val vm = HomeViewModel(TODO())
    val state = vm.uiState.collectAsState()
    HourlyColumn(weatherState = state)
}