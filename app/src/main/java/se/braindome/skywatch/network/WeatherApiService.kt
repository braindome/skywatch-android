package se.braindome.skywatch.network

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/"

interface WeatherApiService {
    @GET("data/3.0/onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String

    ) : ForecastResponse
}

object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val api: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}