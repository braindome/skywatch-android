package se.braindome.skywatch.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import timber.log.Timber
import java.time.LocalTime

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Blue80 = Color(0xFF82B1FF)
val BlueGrey80 = Color(0xFF8E99F3)
val LightBlue80 = Color(0xFF80D8FF)

val Blue40 = Color(0xFF2979FF)
val BlueGrey40 = Color(0xFF5C6BC0)
val LightBlue40 = Color(0xFF40C4FF)

val Blue20 = Color(0xFF82B1FF)
val BlueGrey20 = Color(0xFF8E99F3)
val LightBlue20 = Color(0xFF80D8FF)

val Blue10 = Color(0xFF2979FF)
val BlueGrey10 = Color(0xFF5C6BC0)
val LightBlue10 = Color(0xFF40C4FF)

val nightBackground = Color(0xFF000080)
val clearBackground = Color(0xFFFFA500)
val rainyBackground = Color(0xFF4A90E2)
val cloudyBackground = Color(0xFF708090)


@RequiresApi(Build.VERSION_CODES.O)
fun getBackgroundColor(
    localTimeString: String,
    sunriseString: String,
    sunsetString: String,
    weatherCondition: String?
): Color {
    val localTime = LocalTime.parse(localTimeString)
    val sunrise = LocalTime.parse(sunriseString)
    val sunset = LocalTime.parse(sunsetString)

    Timber.tag("getBackgroundColor").d("Parsed localTime: $localTime, sunrise: $sunrise, sunset: $sunset")

    val isDayTime = if (sunset.isBefore(sunrise)) {
        localTime.isAfter(sunrise) || localTime.isBefore(sunset)
    } else {
        localTime.isAfter(sunrise) && localTime.isBefore(sunset)
    }

    Timber.tag("getBackgroundColor").d("localTime: $localTime, sunrise: $sunrise, sunset: $sunset, isDayTime: $isDayTime")

    return when {
        weatherCondition == "clear sky" && isDayTime -> clearBackground
        weatherCondition == "clear sky" && !isDayTime -> nightBackground
        weatherCondition in listOf("rain", "snow", "shower rain", "thunderstorm", "light rain") -> rainyBackground
        weatherCondition in listOf("few clouds", "scattered clouds", "broken clouds", "overcast clouds") -> cloudyBackground
        else -> Color.LightGray
    }
}