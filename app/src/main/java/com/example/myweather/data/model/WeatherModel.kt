package com.example.myweather.data.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout

data class  WeatherUiModel(
    val condition: WeatherCondition,
    val backgroundColor: Color,
    val textColor: Color,
    val searchBgColor: Color,
    val searchTextColor: Color,
    val iconType: WeatherIconType,
    val temperature: String,
    val city: String,
    val date: String,
    val country:String,

)

enum class WeatherCondition{
    SUNNY, CLOUDY, RAINY,STORMY,WINDY
}
enum class WeatherIconType {
    SUN, CLOUD,RAIN,STORMY,WIND
}
@Composable
fun Modifier.vertical()=
    layout{
        measurable, constraints ->
        val placeable = measurable.measure(constraints)
            layout(placeable.height, placeable.width){
                placeable.place(
                    x=-(placeable.width/2-placeable.height/2),
                    y=-(placeable.height/2-placeable.width/2)
                )
        }

    }