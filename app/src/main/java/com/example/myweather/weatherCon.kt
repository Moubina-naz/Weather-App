package com.example.myweather

import androidx.compose.ui.graphics.Color

data class  WeatherUiModel(
    val condition: WeatherCondition,
    val backgroundColor: Color,
    val iconType: WeatherIconType,
    val temperature: String,
    val city: String,
    val date: String,
    val country:String
)

enum class WeatherCondition{
    SUNNY, CLOUDY, RAINY,STORMY,WINDY
}
enum class WeatherIconType {
    SUN, CLOUD,RAIN,STORMY,WIND
}