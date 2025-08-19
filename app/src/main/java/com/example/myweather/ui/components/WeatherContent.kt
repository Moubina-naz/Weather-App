package com.example.myweather.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.data.model.WeatherCondition
import com.example.myweather.data.model.WeatherIconType
import com.example.myweather.data.model.WeatherUiModel
import com.example.myweather.data.model.vertical

@Composable
fun WeatherContent(weatherUi: WeatherUiModel,
                   onAddCity: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(weatherUi.backgroundColor)
            .padding(0.dp),

    ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
                     // Takes up all remaining space
            ) {
                Text(text = weatherUi.date, fontSize = 23.sp, style = MaterialTheme.typography.titleMedium,color =weatherUi.textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = weatherUi.city, fontSize = 30.sp, style = MaterialTheme.typography.titleLarge,color =weatherUi.textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = weatherUi.country, fontSize = 23.sp, style = MaterialTheme.typography.titleMedium,color =weatherUi.textColor)
            }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Text(
                text = weatherUi.condition.name,
                modifier = Modifier
                    .vertical()
                    .rotate(90f)
                    .padding(end = 8.dp),
                fontSize = 65.sp,
                style = MaterialTheme.typography.headlineMedium,
                color =weatherUi.textColor

            )
        }



        val iconPainter = when (weatherUi.iconType) {
            WeatherIconType.SUN -> painterResource(R.drawable.sun)
            WeatherIconType.CLOUD -> painterResource(R.drawable.cloud)
            WeatherIconType.RAIN -> painterResource(R.drawable.rain)
            WeatherIconType.STORMY -> painterResource(R.drawable.storm)
            WeatherIconType.WIND -> painterResource(R.drawable.windy)
            else -> painterResource(R.drawable.windy)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = iconPainter,
            contentDescription = "Weather Icon: ${weatherUi.iconType.name}",
            modifier = Modifier
                .fillMaxWidth()           // Full width
                .align(Alignment.Center), // Center the image
            contentScale = ContentScale.FillWidth // Stretch to fill width, preserve aspect
        )

       Row(modifier = Modifier
           .fillMaxWidth()
           .align(Alignment.BottomStart)
           .padding(horizontal = 16.dp, vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.Bottom) {
            Text(
                text = weatherUi.temperature,
                fontSize = 90.sp,

                style = MaterialTheme.typography.bodyLarge,
                color = weatherUi.textColor
            )
           Icon(imageVector = Icons.Default.Add , contentDescription ="search", tint = weatherUi.textColor, modifier = Modifier.size(40.dp).clickable { onAddCity?.invoke() } )
        }
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
                        textColor = Color.Black,
                        searchBgColor = Color(0xFFEAE4CA),
                        searchTextColor = Color.Black,
                        temperature = "72°F",
                        city = "New York",
                        date = "July 25, 2023",
                        country = "United States"
                    )
                )
            }
        }

}

