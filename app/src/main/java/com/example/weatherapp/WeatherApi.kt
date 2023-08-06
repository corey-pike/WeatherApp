//WeatherApi.kt
package com.example.weatherapp

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String
    ): Flow<@JvmSuppressWildcards WeatherData>

    @GET("forecast/daily")
    suspend fun getWeatherForecast(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String
    ): Flow<@JvmSuppressWildcards WeatherData>
}
