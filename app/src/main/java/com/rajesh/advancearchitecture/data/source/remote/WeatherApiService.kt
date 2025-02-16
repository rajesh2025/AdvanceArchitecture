package com.rajesh.advancearchitecture.data.source.remote

import com.rajesh.advancearchitecture.data.model.CurrentWeather
import com.rajesh.advancearchitecture.data.model.Forecast
import retrofit2.http.GET

interface WeatherApiService {
    @GET("current")
    suspend fun getCurrentWeather(): CurrentWeather

    @GET("forecast")
    suspend fun getForecast(): Forecast
}