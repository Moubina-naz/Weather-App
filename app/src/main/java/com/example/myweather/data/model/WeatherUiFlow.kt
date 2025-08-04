package com.example.myweather.data.model

sealed class WeatherUiFlow {
    object Loading : WeatherUiFlow()
    data class LocationWeather(val lat: Double, val lon: Double) : WeatherUiFlow()
    object ShowSearch : WeatherUiFlow()
}
