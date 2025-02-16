package com.rajesh.advancearchitecture.domain.usecases

import com.rajesh.advancearchitecture.data.model.CurrentWeather
import com.rajesh.advancearchitecture.data.model.Forecast
import com.rajesh.advancearchitecture.domain.repository.WeatherRepository

class WeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(): Pair<CurrentWeather?, Forecast?> =
        weatherRepository.fetchWeatherData()
}