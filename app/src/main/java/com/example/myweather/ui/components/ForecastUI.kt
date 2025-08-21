package com.example.myweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myweather.data.api.NetworkResponse
import com.example.myweather.data.model.Forecastui
import com.example.myweather.data.model.WeatherScrn
import com.example.myweather.data.model.toUiModel
import com.example.myweather.viewmodel.ApiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastUI( viewModel: ApiViewModel,
               navController: NavController,
               city : String) {


    LaunchedEffect(city) {
        viewModel.getData(city)
    }
    val weatherResult by viewModel.weatherResult.observeAsState()
    when (val result = weatherResult) {
        is NetworkResponse.Loading -> {
            Text("Loading...")
        }

        is NetworkResponse.Success -> {
            val forecast = result.data
            val daily = viewModel.getDailyForecast(forecast).take(5)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                Spacer(Modifier.height(50.dp))

                Text(text = forecast.city?.name ?: "", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(40.dp))

                val uiModel = forecast.toUiModel()
                Text("Temperature Forecast", style = MaterialTheme.typography.labelSmall)
                CityGraph(daily)

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    IconButton(
                        onClick = {  if (uiModel != null) {
                            viewModel.addCity(uiModel)   // directly save
                            navController.navigate(WeatherScrn){
                                popUpTo(Forecastui){ inclusive = true }
                                launchSingleTop = true
                            }
                            // go back to WeatherScreen
                        } },
                        modifier = Modifier
                            .align(Alignment.BottomCenter) // center bottom
                            .size(60.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp) // icon inside
                        )
                    }

                }

            }
        }

        is NetworkResponse.Error -> {
            Text("Error: ${result.message}")
        }

        null -> {}

    }
}


