package com.example.myweather

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun WeatherContent(weatherUi: WeatherUiModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(weatherUi.backgroundColor)
            .padding(8.dp),
        //contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // LEFT SIDE TEXTS
            Column(
                modifier = Modifier
                    .weight(1f)  // Takes up all remaining space
            ) {
                Text(text = weatherUi.date, fontSize = 18.sp, style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = weatherUi.city, fontSize = 25.sp, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = weatherUi.country, fontSize = 18.sp, style = MaterialTheme.typography.bodySmall)
            }

            // RIGHT SIDE SUNNY TEXT
            Text(
                text = weatherUi.condition.name,
                modifier = Modifier
                    .rotate(90f)
                    .padding(end = 8.dp),
                fontSize = 40.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }


        val iconPainter = when (weatherUi.iconType) {
            WeatherIconType.SUN -> painterResource(R.drawable.sunny)
            WeatherIconType.CLOUD -> painterResource(R.drawable.cloudy)
            WeatherIconType.RAIN -> painterResource(R.drawable.rainy)
            WeatherIconType.STORMY -> painterResource(R.drawable.stormy)
            WeatherIconType.WIND -> painterResource(R.drawable.windy)
            else -> painterResource(R.drawable.windy)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = iconPainter,
            contentDescription = "Weather Icon: ${weatherUi.iconType.name}",
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(1f)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )

        Text(
            text = weatherUi.temperature,
            fontSize = 80.sp,
            modifier = Modifier.align(Alignment.BottomStart),
            style = MaterialTheme.typography.displayMedium
        )
    }
}


@Preview
@Composable
fun WeatherContentPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            WeatherContent(
                    weatherUi = WeatherUiModel(
                        condition = WeatherCondition.SUNNY,
                        backgroundColor = Color(0xFFEAE4CA),
                        iconType = WeatherIconType.STORMY,
                        temperature = "72°F",
                        city = "New York",
                        date = "July 25, 2023",
                        country = "United States"
                    )
                )
            }
        }

}

