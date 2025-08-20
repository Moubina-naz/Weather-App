package com.example.myweather.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myweather.data.api.NetworkResponse
import com.example.myweather.ui.components.ForecastUI
import com.example.myweather.viewmodel.ApiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CityScreen( viewModel: ApiViewModel ,
               navController: NavController,
               city : String){

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
            //ForecastUI(forecast,viewModel)
        }

        is NetworkResponse.Error -> {
            Text("Error: ${result.message}")
        }

        null -> {}
    }

}