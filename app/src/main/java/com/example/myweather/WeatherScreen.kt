package com.example.myweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.API.NetworkResponse
import com.example.myweather.API.toUiModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WeatherScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier){
    var showContent by remember { mutableStateOf(false) }
    val weatherResult by viewModel.weatherResult.observeAsState()

    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(8.dp)) {
        SearchBars(viewModel = viewModel,
            onSearch = { city ->
                viewModel.getData(city) // this triggers the API call
                showContent = true      // (optional) to show more data
            })
        Spacer(modifier = Modifier.height(4.dp))
        DayChooser()
        when (val result = weatherResult) {
            is NetworkResponse.Loading -> {
                Text("Loading...", modifier = Modifier.padding(8.dp))
            }
            is NetworkResponse.Success -> {
                val uiModel = result.data.toUiModel()
                if (uiModel != null) {
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