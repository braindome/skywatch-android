package se.braindome.skywatch.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

const val BASE_URL = "https://api.openweathermap.org/"

interface WeatherApiService {
    @GET("data/3.0/onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String,
        @Query("units") units : String

    ) : ForecastResponse
}

object RetrofitInstance {

    private val loggingInterceptor = LoggingInterceptor()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val api: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.tag("Interceptor").d("Sending request: ${request.url} \n ${request.headers}")
        val response = chain.proceed(request)
        Timber.tag("Interceptor").d("Received response for: ${response.request.url} \n ${response.headers}")
        return response
    }
}
