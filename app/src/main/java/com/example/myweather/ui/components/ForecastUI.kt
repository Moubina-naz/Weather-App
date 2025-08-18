package com.example.myweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.viewmodel.ApiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastUI(forecast: WeatherResponse,viewModel: ApiViewModel,){

    val daily = viewModel.getDailyForecast(forecast).take(5)

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)) {

        Spacer(Modifier.height(50.dp))

        Text(text = forecast.city?.name ?: "", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(40.dp))


        Text("Temperature Forecast", style = MaterialTheme.typography.labelSmall)
        CityGraph(daily)

        Box(modifier = Modifier.padding(10.dp))

    }
}


