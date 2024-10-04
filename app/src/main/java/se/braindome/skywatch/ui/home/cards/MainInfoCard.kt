package se.braindome.skywatch.ui.home.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.braindome.skywatch.R
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.theme.Typography

@Composable
fun MainInfoCard(
    weatherState: State<HomeUiState>,
) {
    val windDeg = weatherState.value.forecastResponse?.current?.wind_deg ?: 0.0
    val windIcon = when (windDeg) {
        in 337..360, in 0..22 -> R.drawable.wi_direction_up
        in 22..67 -> R.drawable.wi_direction_up_right
        in 67..112 -> R.drawable.wi_direction_right
        in 112..157 -> R.drawable.wi_direction_down_right
        in 157..202 -> R.drawable.wi_direction_down
        in 202..247 -> R.drawable.wi_direction_down_left
        in 247..292 -> R.drawable.wi_direction_left
        in 292..337 -> R.drawable.wi_direction_up_left
        else -> R.drawable.wi_direction_up // Default case
    }
    val imageBaseUrl = "https://openweathermap.org/img/wn/"
    val weatherIconUrl = weatherState.value.forecastResponse?.current?.weather?.get(0)?.icon
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .border(width = 1.dp, color = Color.Black)
            .padding(horizontal = 16.dp),
        ) {
        Column(
            modifier = Modifier.padding(0.dp),
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = weatherState.value.forecastResponse?.current?.temp.toString(),
                        style = Typography.displayLarge,
                        //modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = weatherState.value.forecastResponse?.current?.weather?.get(0)?.description.toString(),
                        style = Typography.headlineLarge,
                        //modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                AsyncImage(
                    model = "$imageBaseUrl$weatherIconUrl@4x.png",
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Crop
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
    Column {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
        )
        Text(primaryText)
        Text(secondaryText)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainInfoCardPreview() {
    //MainInfoCard("35C", "Sunny", "01d")
}
