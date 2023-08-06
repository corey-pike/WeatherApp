//DataClasses.kt
package com.example.weatherapp

import com.squareup.moshi.Json

data class ForecastTemp(
    val day: Float,
    val min: Float,
    val max: Float
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Sys(
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class WeatherData(
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int,
    val temp: ForecastTemp,
)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val population: Int
)

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
)

data class ListItem(
    val dt: Long,
    val temp: Temp,
    val pressure: Double,
    val humidity: Int,
    val weather: List<Weather>,
    val deg: Int,
)

data class ForecastData(
    val city: City,
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<ListItem>
)
