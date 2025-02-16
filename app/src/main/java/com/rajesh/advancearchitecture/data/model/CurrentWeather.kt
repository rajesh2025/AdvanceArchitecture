package com.rajesh.advancearchitecture.data.model

data class CurrentWeather(
    val temprature: Double,
    val humidity: Int,
    val condition: String
)

data class Forecast(
    val daily: List<DailyForecast>
)

data class DailyForecast(
    val date: String,
    val temprature: Double,
    val condition: String
)
