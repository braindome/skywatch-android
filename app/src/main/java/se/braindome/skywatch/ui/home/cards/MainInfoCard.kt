package se.braindome.skywatch.ui.home.cards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.R
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.theme.Typography
import se.braindome.skywatch.ui.theme.getBackgroundColor
import se.braindome.skywatch.ui.utils.DateTimeUtils
import se.braindome.skywatch.ui.utils.IconResourceProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainInfoCard(
    weatherState: State<HomeUiState>,
) {
    val windDeg = weatherState.value.forecastResponse?.current?.wind_deg?.toDouble() ?: 0.0
    val windIcon = IconResourceProvider.getWindIcon(windDeg)

    val sunset = weatherState.value.forecastResponse?.daily?.get(0)?.sunset
    val sunrise = weatherState.value.forecastResponse?.daily?.get(0)?.sunrise
    val localTime = DateTimeUtils.convertToLocalTime(
        weatherState.value.forecastResponse?.current?.dt ?: 0, true
    ).first


    val localSunrise = DateTimeUtils.convertToLocalTime(sunrise ?: 0, format24 = true).first
    val localSunset = DateTimeUtils.convertToLocalTime(sunset ?: 0, format24 = true).first

    val backgroundColor = getBackgroundColor(
        localTimeString = localTime,
        sunriseString = localSunrise,
        sunsetString = localSunset,
        weatherCondition = weatherState.value.forecastResponse?.current?.weather?.get(0)?.description
    )

    val weatherIconUrl = weatherState.value.forecastResponse?.current?.weather?.get(0)?.icon
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(backgroundColor, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
        ) {
        Column(
            modifier = Modifier
                .padding(0.dp)
                .background(backgroundColor),
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = weatherState.value.location.toString(),
                        style = Typography.headlineMedium,
                        color = Color.White,
                    )
                    Text(
                        text = weatherState.value.forecastResponse?.current?.temp?.toInt().toString() + " \u00B0C",
                        style = Typography.headlineMedium,
                        color = Color.White,
                    )
                    Text(
                        text = weatherState.value.forecastResponse?.current?.weather?.get(0)?.description.toString(),
                        style = Typography.bodyLarge,
                        color = Color.White,
                    )
                }
                Icon(
                    painter = painterResource(id = IconResourceProvider.assignIcon(weatherIconUrl.toString())),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(150.dp)
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                RowItem(
                    iconRes = R.drawable.wi_umbrella,
                    primaryText = weatherState.value.forecastResponse?.daily?.get(0)?.pop.toString() + "%",
                    secondaryText = "TODO()"
                )
                RowItem(
                    iconRes = windIcon,
                    primaryText = weatherState.value.forecastResponse?.daily?.get(0)?.wind_speed.toString(),
                    secondaryText = "TODO()"
                )
                RowItem(
                    iconRes = R.drawable.wi_day_sunny,
                    primaryText = "UV " +weatherState.value.forecastResponse?.current?.uvi.toString(),
                    secondaryText = "TODO()"
                )
                RowItem(
                    iconRes = R.drawable.wi_humidity,
                    primaryText = weatherState.value.forecastResponse?.current?.humidity.toString() + "%",
                    secondaryText = "TODO()"
                )

            }
        }
    }
}

@Composable
fun RowItem(
    iconRes: Int,
    primaryText: String,
    secondaryText: String
) {
    Column(
        modifier = Modifier.padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.White,
        )
        Text(primaryText, color = Color.White)
        //Text(secondaryText, color = Color.White)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainInfoCardPreview() {
    MainInfoCard(weatherState = remember { mutableStateOf(HomeUiState(forecastResponse = null, loading = false))})
}
