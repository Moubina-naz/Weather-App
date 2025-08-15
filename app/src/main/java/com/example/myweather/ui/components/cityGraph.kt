package com.example.myweather.ui.components

import androidx.compose.runtime.Composable
import com.example.myweather.data.model.DailyForecast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CityGraph(daily: List<DailyForecast>) {
    Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        val maxTemp = daily.maxOf { it.maxTemp }
        val minTemp = daily.minOf { it.minTemp }
        val tempRange = maxTemp - minTemp

        val stepX = size.width / (daily.size - 1)
        val stepY = size.height / tempRange.toFloat()

        daily.forEachIndexed { index, day ->
            val x = (index * stepX)
            val y = size.height - ((day.maxTemp - minTemp) * stepY).toFloat()


            // Draw point
            drawCircle(
                color = Color.White,
                radius = 6f,
                center = Offset(x, y)
            )

            // Draw line to previous point
            if (index > 0) {
                val prevX = (index - 1) * stepX
                val prevY = size.height - ((daily[index - 1].maxTemp - minTemp) * stepY).toFloat()
                drawLine(
                    color = Color.White,
                    start = Offset(prevX, prevY),
                    end = Offset(x, y),
                    strokeWidth = 4f
                )
            }

        }
    }
}
