package se.braindome.skywatch.network

data class ForecastResponse(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val hourly: List<Hourly>?,
    val minutely: List<Minutely>?,
    val timezone: String,
    val timezone_offset: Int
)

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int?,
    val wind_gust: Double?,
    val wind_speed: Double?
)

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: FeelsLike,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double?,
    val snow: Double?,
    val summary: String,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val temp: Double,
    val uvi: Double?,
    val visibility: Int?,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

data class Minutely(
    val dt: Int,
    val precipitation: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)


val mockForecastResponse = ForecastResponse(
    current = Current(
        clouds = 20,
        dew_point = 5.0,
        dt = 1625247600,
        feels_like = 6.0,
        humidity = 60,
        pressure = 1012,
        sunrise = 1625205600,
        sunset = 1625260800,
        temp = 8.0,
        uvi = 0.0,
        visibility = 10000,
        weather = listOf(Weather(description = "Mostly Clear", icon = "01d", id = 800, main = "Clear")),
        wind_deg = 0,
        wind_gust = 0.0,
        wind_speed = 0.0
    ),
    daily = listOf(
        Daily(
            clouds = 20,
            dew_point = 5.0,
            dt = 1625247600,
            feels_like = FeelsLike(day = 6.0, eve = 5.0, morn = 4.0, night = 3.0),
            humidity = 60,
            moon_phase = 0.5,
            moonrise = 1625205600,
            moonset = 1625260800,
            pop = 0.0,
            pressure = 1012,
            rain = null,
            snow = null,
            summary = "Clear sky",
            sunrise = 1625205600,
            sunset = 1625260800,
            temp = Temp(day = 8.0, eve = 7.0, max = 10.0, min = 5.0, morn = 6.0, night = 4.0),
            uvi = 0.0,
            weather = listOf(Weather(description = "Clear sky", icon = "01d", id = 800, main = "Clear")),
            wind_deg = 0,
            wind_gust = 0.0,
            wind_speed = 0.0
        )
    ),
    lat = 57.70,
    lon = 11.96,
    minutely = null,
    timezone = "Europe/Stockholm",
    timezone_offset = 7200,
    hourly = null

)