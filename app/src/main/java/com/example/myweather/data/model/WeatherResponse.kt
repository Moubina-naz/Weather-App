package com.example.myweather.data.model

import android.util.Log
import com.example.myweather.ui.theme.getWeatherPalette
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeatherResponse(
    val cod: String,
    val message: Int?=null,
    val cnt: Int?=null,
    val list: List<WeatherItem> = emptyList(),
    val city: City?=null
)

data class WeatherItem(
    val dt: Long,
    val main: Main?=null,
    val weather: List<Weather> = emptyList(),
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    @SerializedName("dt_txt") val dtTxt: String
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    @SerializedName("sea_level") val seaLevel: Int,
    @SerializedName("grnd_level") val grndLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf") val tempKf: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(val all: Int)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class Rain(
    @SerializedName("3h") val volume: Double
)

data class Sys(val pod: String)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

data class Coord(val lat: Double, val lon: Double)

fun WeatherResponse.toUiModel(): WeatherUiModel? {

    if (list.isEmpty() || city == null) {
        Log.w("WeatherData", "Incomplete weather data: list=${list.size}, city=${city}")
        return null
    }
    val weatherItem = list.first()
    val weather = weatherItem.weather.firstOrNull()
    val main = weatherItem.main

    val condition = when(weather?.main?.lowercase()){
        "clear" -> WeatherCondition.SUNNY
        "rain" -> WeatherCondition.RAINY
        //"snow" -> "SNOWY"
        "clouds" -> WeatherCondition.CLOUDY
        "thunderstorm" -> WeatherCondition.STORMY
        "Windy" -> WeatherCondition.WINDY
        else -> WeatherCondition.SUNNY

    }
    val iconType=when(condition){
        WeatherCondition.SUNNY -> WeatherIconType.SUN
        WeatherCondition.RAINY -> WeatherIconType.RAIN
        WeatherCondition.CLOUDY -> WeatherIconType.CLOUD
        WeatherCondition.STORMY -> WeatherIconType.STORMY
        WeatherCondition.WINDY -> WeatherIconType.WIND
    }
    val palette = getWeatherPalette(condition)
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val date = dateFormat.format(this.list.firstOrNull()?.dt?.times(1000 ?: 0)?.let { Date(it) });
        return WeatherUiModel(
            condition = condition,
            backgroundColor = palette.background,
            textColor = palette.textPrimary,
            searchBgColor = palette.searchbg,
            searchTextColor = palette.searchtext,
            iconType = iconType,
            temperature = "${main?.temp?.toInt() ?: 0}°C ",
            city = this.city.name,
            date = date,
            country = city?.country?.let { code ->
                Locale("", code).displayCountry
            } ?: "Unknown Country"

        )
}