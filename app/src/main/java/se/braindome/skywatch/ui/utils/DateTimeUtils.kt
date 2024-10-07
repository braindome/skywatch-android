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
    fun convertToLocalTime(dt: Int, format24: Boolean): String {
        val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dt.toLong()), ZoneId.systemDefault())
        return if (format24) {
            localDateTime.format(formatter24Hour)
        } else {
            localDateTime.format(formatter12Hour)
        }
    }

}