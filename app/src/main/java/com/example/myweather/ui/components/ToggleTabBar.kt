package com.example.myweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.data.model.WeatherCondition
import com.example.myweather.ui.theme.getWeatherPalette

@Composable
fun DayChooser(
                     modifier: Modifier = Modifier,weatherCondition: WeatherCondition
){
    val options = listOf("TODAY", "TOMORROW", "6 DAYS")
    val selectedOption = "Daily"
    val palette = getWeatherPalette(weatherCondition)
    Row(
        modifier = modifier.fillMaxWidth()
            .background(palette.background)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            //val bgColor = if (isSelected) Color(0xFF8AD4D4) else Color(0xFFEAE4CA)
            val textColor = palette.textPrimary
           // val borderColor = if (isSelected) Color.Transparent else Color(0xFFEAE4CA)

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(palette.background)
                    .border(width = 0.dp,
                        color = palette.background,
                    ).clickable {  }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = option,
                    color = textColor,
                    fontSize = 19.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DayChooserPreview() {
    //DayChooser()
}