package com.example.myweather.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.viewmodel.ApiViewModel

@Composable
fun ForecastUI(forecast: WeatherResponse,viewModel: ApiViewModel,){

    val daily = viewModel.getDailyForecast(forecast).take(5)

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = forecast.city?.name ?: "", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))
LazyRow(){items(daily.size){ i ->
    val day = daily[i]
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = if (i == 0) "Today" else day.date, fontSize = 14.sp,)
        Text(text = "${day.maxTemp.toInt()}° / ${day.minTemp.toInt()}°")
        Text(text = "${day.windSpeed} km/h", fontSize = 12.sp)
    }
}
}

    }
}


