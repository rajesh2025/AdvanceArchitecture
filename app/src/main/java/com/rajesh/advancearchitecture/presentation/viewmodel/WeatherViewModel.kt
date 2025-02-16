package com.rajesh.advancearchitecture.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajesh.advancearchitecture.data.model.CurrentWeather
import com.rajesh.advancearchitecture.data.model.Forecast
import com.rajesh.advancearchitecture.domain.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchWeather() {
        viewModelScope.launch {
            val (currentWeather, forecast) = repository.fetchWeatherData()
            if (currentWeather != null && forecast != null) {
                _weatherData.value = WeatherData(currentWeather, forecast)
            } else {
                _errorMessage.value = "Failed to fetch weather data"
            }
        }
    }
}


data class WeatherData(
    val currentWeather: CurrentWeather,
    val forecast: Forecast
)