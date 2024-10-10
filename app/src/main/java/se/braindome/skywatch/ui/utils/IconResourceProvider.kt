package se.braindome.skywatch.ui.utils

import se.braindome.skywatch.R
import kotlin.collections.contains

object IconResourceProvider {

    fun assignIcon(iconCode: String): Int {
        return when (iconCode) {
            "01d" -> R.drawable.wi_day_sunny
            "01n" -> R.drawable.wi_night_clear
            "02d" -> R.drawable.wi_day_cloudy
            "02n" -> R.drawable.wi_night_alt_cloudy
            "03d", "03n" -> R.drawable.wi_cloud
            "04d", "04n" -> R.drawable.wi_cloudy
            "09d" -> R.drawable.wi_day_showers
            "09n" -> R.drawable.wi_night_alt_showers
            "10d" -> R.drawable.wi_day_rain
            "10n" -> R.drawable.wi_night_alt_rain
            "11d", "11n" -> R.drawable.wi_thunderstorm
            "13d", "13n" -> R.drawable.wi_snow
            "50d" -> R.drawable.wi_day_fog
            "50n" -> R.drawable.wi_night_fog
            else -> R.drawable.wi
        }
    }


    fun getWindIcon(windDeg: Double): Int {
        return when (windDeg) {
            in 337.0..360.0, in 0.0..22.0 -> R.drawable.wi_direction_up
            in 22.0..67.0 -> R.drawable.wi_direction_up_right
            in 67.0..112.0 -> R.drawable.wi_direction_right
            in 112.0..157.0 -> R.drawable.wi_direction_down_right
            in 157.0..202.0 -> R.drawable.wi_direction_down
            in 202.0..247.0 -> R.drawable.wi_direction_down_left
            in 247.0..292.0 -> R.drawable.wi_direction_left
            in 292.0..337.0 -> R.drawable.wi_direction_up_left
            else -> R.drawable.wi_direction_up // Default case
        }
    }
}