package se.braindome.skywatch.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter24Hour = DateTimeFormatter.ofPattern("HH:mm")

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter12Hour = DateTimeFormatter.ofPattern("hh:mm a")

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalTime(dt: Int, format24: Boolean): Pair<String, String> {
        val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dt.toLong()), ZoneId.systemDefault())
        val formattedTime = if (format24) {
            localDateTime.format(formatter24Hour)
        } else {
            localDateTime.format(formatter12Hour)
        }
        val dayOfWeek = localDateTime.dayOfWeek.name.lowercase().substring(0, 3).replaceFirstChar { it.uppercase() }
        return Pair(formattedTime, dayOfWeek)
    }
}

fun getUviDefinition(uvi: Double): String {
    return when (uvi) {
        in 0.0..2.0 -> "Low"
        in 3.0..5.0 -> "Moderate"
        in 6.0..7.0 -> "High"
        in 8.0..10.0 -> "Very High"
        else -> "Extreme"
    }
}