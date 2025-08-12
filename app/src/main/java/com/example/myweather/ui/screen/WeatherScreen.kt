package com.example.myweather.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myweather.data.api.NetworkResponse
import com.example.myweather.data.model.toUiModel
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.data.model.WeatherCondition
import com.example.myweather.ui.components.DayChooser
import com.example.myweather.ui.components.SearchBars
import com.example.myweather.ui.components.WeatherContent
import com.example.myweather.ui.theme.getWeatherPalette
import com.example.myweather.viewmodel.ApiViewModel

@Composable
fun WeatherScreen(viewModel: ApiViewModel, modifier: Modifier = Modifier,){
    var showContent by remember { mutableStateOf(false) }
    val weatherResult by viewModel.weatherResult.observeAsState()
    val currentCondition = remember { mutableStateOf(WeatherCondition.SUNNY) }

    val palette = getWeatherPalette(currentCondition.value)
    Column(modifier= Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .background(palette.background)
        ) {

        //Spacer(modifier = Modifier.height(4.dp))
        DayChooser(weatherCondition = currentCondition.value)
        /*CityPagerIndicator(
            totalCities = savedCities.size,
            currentCityIndex = currentCityIndex
        )*/

        when (val result = weatherResult) {
            is NetworkResponse.Loading -> {
                Text("Loading...", modifier = Modifier.padding(8.dp))
            }
            is NetworkResponse.Success -> {
                val uiModel = result.data.toUiModel()
                if (uiModel != null) {
                    currentCondition.value = uiModel.condition
                    WeatherContent(weatherUi = uiModel)
                } else {
                    Text("Could not parse weather data", color = Color.Red)
                }
            }
            is NetworkResponse.Error -> {
                Text("Error: ${result.message}", color = Color.Red, modifier = Modifier.padding(8.dp))
            }
            null -> {
                Text("Search for a city", modifier = Modifier.padding(8.dp))
            }


        }
        //WeatherContent(weatherUi)
    }
}
/*@Preview(showBackground = true)
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
}}*/