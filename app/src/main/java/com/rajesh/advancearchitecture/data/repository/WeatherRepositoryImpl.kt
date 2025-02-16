package com.rajesh.advancearchitecture.data.repository

import com.rajesh.advancearchitecture.data.model.CurrentWeather
import com.rajesh.advancearchitecture.data.model.Forecast
import com.rajesh.advancearchitecture.data.source.remote.WeatherApiService
import com.rajesh.advancearchitecture.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun fetchWeatherData(): Pair<CurrentWeather?, Forecast?> {
        return coroutineScope {
            val currentWeatherDeferred = async { fetchCurrentWeather() }
            val forecastDeferred = async { fetchForecast() }
            Pair(currentWeatherDeferred.await(), forecastDeferred.await())
        }
    }

    override suspend fun fetchCurrentWeather(): CurrentWeather? {
        return try {
            return weatherApiService.getCurrentWeather()
        } catch (e: Exception) {
            null
        }

    }

    override suspend fun fetchForecast(): Forecast? {
        return try {
            weatherApiService.getForecast()
        } catch (e: Exception) {
            null
        }
    }
}