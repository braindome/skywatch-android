package se.braindome.skywatch.ui.home.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.braindome.skywatch.ui.theme.Typography

@Composable
fun MainInfoCard(
    currentTemp: String,
    currentWeather: String,
    weatherIconUrl: String
) {
    val imageBaseUrl = "https://openweathermap.org/img/wn/"
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(width = 1.dp, color = Color.Black)
            .padding(16.dp),
        ) {
        Column {
            Row {
                Column(
                    modifier = Modifier.weight(0.5f)

                ) {
                    Text(
                        text = currentTemp,
                        style = Typography.displayLarge,
                        modifier = Modifier.weight(0.5f)

                    )
                    Text(
                        text = currentWeather,
                        style = Typography.headlineLarge,
                        modifier = Modifier.weight(0.5f)
                    )
                }

                AsyncImage(
                    model = "$imageBaseUrl$weatherIconUrl@4x.png",
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(200.dp)
                )


            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainInfoCardPreview() {
    MainInfoCard("35C", "Sunny", "01d")
}
