package com.example.myweather.data.model

data class CitySearchResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)
