//ForecastViewModel.kt
package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val weatherApi: WeatherApi) : ViewModel() {

    init {
        fetchForecastData()
    }

    private val _forecastLiveData: MutableLiveData<WeatherData> = MutableLiveData()
    val forecastLiveData: LiveData<WeatherData> get() = _forecastLiveData

    private fun fetchForecastData() {
        viewModelScope.launch {
            val zipCode = "55125"
            val apiKey = "3723dded195b2c6ec85f31a5c5e0b1ae"
            _forecastLiveData.value = weatherApi.getWeatherForecast(zipCode, apiKey).firstOrNull()
        }
    }
}
