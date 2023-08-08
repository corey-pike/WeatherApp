//DataClasses.kt
package com.example.weatherapp

import com.squareup.moshi.Json

data class Temperature(
    val temp: Double,
    @Json(name = "temp_max") val high: Double,
    @Json(name = "temp_mix") val low: Double,
    @Json(name = "feels_like") val feelsLike: Double
)

data class WeatherCondition(
    val icon: String
)

data class WeatherData(
    @Json(name = "weather") val weatherConditions: List<WeatherCondition>,
    val name: String,
    @Json(name = "main") val temperature: Temperature
) {
    val highTemp: Double
        get() = (temperature.high - 273.15) * 1.8 + 32

    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}

data class ForecastItem(
    @Json(name = "weather") val weatherConditions: List<WeatherCondition>,
    val date: String
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}

data class Forecast(
    val sunrise: String,
    val sunset: String,
    val temperature: Temperature,
    @Json(name = "list") val forecasts: List<ForecastItem>
)