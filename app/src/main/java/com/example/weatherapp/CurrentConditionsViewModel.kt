//CurrentConditionsViewModel.kt
package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    fun fetchCurrentWeather(zip: String, apiKey: String) = viewModelScope.launch {
        _currentWeather.value = apiService.getCurrentWeather(zip, apiKey)
    }

    private val _zipCode = MutableStateFlow("")
    val zipCode: StateFlow<String> = _zipCode

    fun updateZipCode(newZipCode: String) {
        _zipCode.value = newZipCode
    }
}
