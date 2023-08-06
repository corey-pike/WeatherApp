//CurrentConditionsViewModel.kt
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
class CurrentConditionsViewModel @Inject constructor(private val weatherApi: WeatherApi) : ViewModel() {

    private val _currentConditionsLiveData: MutableLiveData<WeatherData> = MutableLiveData()
    val currentConditionsLiveData: LiveData<WeatherData> get() = _currentConditionsLiveData

    init {
        fetchCurrentConditionsData()
    }

    private fun fetchCurrentConditionsData() {
        viewModelScope.launch {
            val zipCode = "55125"
            val apiKey = "3723dded195b2c6ec85f31a5c5e0b1ae"
            _currentConditionsLiveData.value = weatherApi.getCurrentWeather(zipCode, apiKey).firstOrNull()
        }
    }
}
