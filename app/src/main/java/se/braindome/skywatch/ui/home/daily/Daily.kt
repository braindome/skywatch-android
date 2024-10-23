package se.braindome.skywatch.ui.home.daily

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.R
import se.braindome.skywatch.network.mockForecastResponse
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.theme.nightBackground
import se.braindome.skywatch.ui.utils.DateTimeUtils
import se.braindome.skywatch.ui.utils.IconResourceProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyRow(weatherState: State<HomeUiState>) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Icon(
                painter = painterResource(
                    id = if (isExpanded) R.drawable.baseline_expand_less_24 else R.drawable.baseline_expand_more_24,
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(34.dp)
                    .clickable { isExpanded = !isExpanded },
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Next 8 Days",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        if (isExpanded) {
            LazyRow(
                modifier = Modifier//.padding(2.dp)
                    .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                items(8) { index -> DailyItem(weatherState, index) }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyItem(weatherState: State<HomeUiState>, index: Int) {
    val dailyState = weatherState.value.forecastResponse?.daily?.get(index)
    val weatherIcon = dailyState?.weather?.get(0)?.icon

    // Log converted times
    //Timber.tag("ConvertedTimes").d("Converted Sunrise: $localSunrise, Converted Sunset: $localSunset")

    Column(
        modifier = Modifier
            .background(nightBackground, RoundedCornerShape(10.dp))
            .width(100.dp)
            .height(140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = IconResourceProvider.assignIcon(weatherIcon.toString())),
            contentDescription = "weather icon",
            tint = Color.White,
            modifier = Modifier
                .size(42.dp)
        )
        Text(
            text = DateTimeUtils.formatDate(dailyState?.dt ?: 0),
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = dailyState?.temp?.max?.toInt().toString() + "°C",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )
        Text(
            text = dailyState?.temp?.min?.toInt().toString() + "°C",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DailyItemPreview() {
    DailyItem(weatherState = remember {
        mutableStateOf(
            HomeUiState(
                forecastResponse = mockForecastResponse,
                loading = false,
                location = "Gothenburg"
            )
        )
    }, index = 0)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DailyRowPreview() {
    DailyRow(weatherState = remember {
        mutableStateOf(
            HomeUiState(
                forecastResponse = mockForecastResponse,
                loading = false,
                location = "Gothenburg"
            )
        )
    })
}