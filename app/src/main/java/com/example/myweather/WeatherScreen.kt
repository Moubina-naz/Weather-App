package com.example.myweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen(weatherUi: WeatherUiModel){

    Column(modifier= Modifier
        .fillMaxSize()
        .background(color = weatherUi.backgroundColor)
        .padding(8.dp)) {
        SearchBars()
        Spacer(modifier = Modifier.height(4.dp))
        DayChooser()
        WeatherContent(weatherUi)
    }
}
@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview(){

    WeatherScreen( weatherUi = WeatherUiModel(
        condition = WeatherCondition.SUNNY,
        backgroundColor = Color(0xFFEAE4CA),
        iconType = WeatherIconType.SUN,
        temperature = "72°F",
        city = "New York",
        date = "July 25, 2023",
        country = "United States"
    ))
}