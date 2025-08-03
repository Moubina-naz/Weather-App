package com.example.myweather.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.myweather.data.model.WeatherCondition

data class WeatherColorPalette(
    val background: Color,
    val textPrimary: Color,
    val searchbg: Color,
    val searchtext: Color
)
val SunnyPalette = WeatherColorPalette(
    background = Color(0xFFEAE4CA),
    textPrimary = Color(0xFF191A1C),
    searchbg = Color(0xFFF4EDDA),
    searchtext = Color(0xFF191A1C)
)

val WindyPalette = WeatherColorPalette(
    background = Color(0xFF9A5859),
    textPrimary = Color(0xFFD3DCD7),
    searchbg = Color(0xFFAA6367),
    searchtext = Color(0xFFD3DCD7)
)
val RainyPalette = WeatherColorPalette(
    background = Color(0xFF233947),
    textPrimary = Color(0xFFdfd9d4),
    searchbg = Color(0xFF2B4856),
    searchtext = Color(0xFFdfd9d4)
)

val StormyPalette = WeatherColorPalette(
    background = Color(0xFF1B1C1E),
    textPrimary = Color(0xFFE7DFC8),
    searchbg = Color(0xFF292A2C),
    searchtext = Color(0xFFE7DFC8)
)

val CloudyPalette = WeatherColorPalette(
    background = Color(0xFFD4D9D3),
    textPrimary = Color(0xFF213B4C),
    searchbg = Color(0xFFDDDFDC),
    searchtext = Color(0xFF213B4C)
)
fun getWeatherPalette(condition: WeatherCondition): WeatherColorPalette {
    return when (condition) {
        WeatherCondition.SUNNY -> SunnyPalette
        WeatherCondition.CLOUDY -> CloudyPalette
        WeatherCondition.RAINY -> RainyPalette
        WeatherCondition.STORMY -> StormyPalette
        WeatherCondition.WINDY -> WindyPalette
    }
}