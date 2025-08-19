package com.example.myweather.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.data.model.WeatherCondition
import com.example.myweather.data.model.WeatherUiModel
import com.example.myweather.ui.components.DayChooser
import com.example.myweather.ui.components.SearchBars
import com.example.myweather.ui.components.WeatherContent
import com.example.myweather.ui.theme.getWeatherPalette
import com.example.myweather.viewmodel.ApiViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherScreen(viewModel: ApiViewModel, modifier: Modifier = Modifier) {
    val weatherResult by viewModel.weatherResult.observeAsState()
    val currentCondition = remember { mutableStateOf(WeatherCondition.SUNNY) }

    // ✅ Saved cities list (starts empty)
    var savedCities by remember { mutableStateOf(listOf<WeatherUiModel>()) }

    // Pager state
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { savedCities.size }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(getWeatherPalette(currentCondition.value).background)
    ) {
        DayChooser(weatherCondition = currentCondition.value)

        when (val result = weatherResult) {
            is NetworkResponse.Loading -> {
                Text("Loading...", modifier = Modifier.padding(8.dp))
            }
            is NetworkResponse.Success -> {
                val uiModel = result.data.toUiModel()
                if (uiModel != null) {
                    currentCondition.value = uiModel.condition

                    // ✅ Add "+" button logic
                    WeatherContent(
                        weatherUi = uiModel,
                        onAddCity = {
                            if (!savedCities.contains(uiModel)) {
                                savedCities = savedCities + uiModel
                            }
                        }
                    )
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

        // ✅ Pager & dots only when cities exist
        if (savedCities.isNotEmpty()) {
            HorizontalPager(state = pagerState) { page ->
                WeatherContent(weatherUi = savedCities[page])
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(savedCities.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                            .background(
                                if (pagerState.currentPage == index) Color.Black else Color.Gray,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
            }
        }
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