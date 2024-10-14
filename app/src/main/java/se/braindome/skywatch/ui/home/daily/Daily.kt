package se.braindome.skywatch.ui.home.daily

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.R
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.utils.DateTimeUtils
import se.braindome.skywatch.ui.utils.IconResourceProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyColumn(weatherState: State<HomeUiState>) {
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
                modifier = Modifier.padding(start = 8.dp)
                    .size(34.dp).clickable { isExpanded = !isExpanded },
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
            LazyColumn(
                modifier = Modifier//.padding(2.dp)
                    .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent),

                ) {
                items(24) { index -> DailyItem(weatherState, index) }
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


   val backgroundColor: Color = TODO() // Get bg color by daily forecast


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
                    dt = dailyState?.dt ?: 0,
                    format24 = true
                ).toString(),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
            Text(
                text = dailyState?.summary.toString(),
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
                text = dailyState?.feels_like?.day?.toInt().toString() + " \u00B0C",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
            Text(
                text = dailyState?.feels_like?.night?.toInt().toString() + " \u00B0C",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
    }
}