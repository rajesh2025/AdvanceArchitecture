package com.rajesh.advancearchitecture.domain.repository

import com.rajesh.advancearchitecture.data.model.CurrentWeather
import com.rajesh.advancearchitecture.data.model.Forecast

interface WeatherRepository {

    suspend fun fetchWeatherData(): Pair<CurrentWeather?, Forecast?>

    suspend fun fetchCurrentWeather(): CurrentWeather?

    suspend fun fetchForecast(): Forecast?
}