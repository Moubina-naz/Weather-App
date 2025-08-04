package com.example.myweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CityPagerIndicator(
    totalCities: Int,
    currentCityIndex: Int
){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)){repeat(totalCities) { index ->
        val isSelected = index == currentCityIndex
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(if (isSelected) 10.dp else 6.dp)
                .background(
                    color = if (isSelected) Color.White else Color.LightGray,
                    shape = CircleShape
                )
        )
    }

    }
}