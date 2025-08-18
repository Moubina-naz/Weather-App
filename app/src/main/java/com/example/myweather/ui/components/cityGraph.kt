package com.example.myweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.myweather.data.model.DailyForecast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CityGraph(daily: List<DailyForecast>,
              modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val cellWidth = 80.dp // fixed block width

    Column(
        modifier = modifier
            .horizontalScroll(scrollState)
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier.width(cellWidth * daily.size)
        ) {
            daily.forEachIndexed { index, day ->
                val label = when (index) {
                    0 -> "Today"
                    1 -> "Tom"
                    else -> {
                        val parsed =
                            LocalDate.parse(day.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        parsed.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    }
                }

                Column(
                    modifier = Modifier.width(cellWidth),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = day.date.takeLast(5), // "14/08"
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .height(200.dp)
                .width(cellWidth * daily.size)
                .background(Color.Transparent),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val maxTemp = daily.maxOf { it.maxTemp }
                val minTemp = daily.minOf { it.minTemp }
                val tempRange = (maxTemp - minTemp).coerceAtLeast(1.0)

                val stepX = size.width / (daily.size - 1).toFloat()
                val stepY = size.height / tempRange.toFloat()

                daily.forEachIndexed { index, day ->
                    val x = index * stepX
                    val yMax = size.height - ((day.maxTemp - minTemp) * stepY).toFloat()
                    val yMin = size.height - ((day.minTemp - minTemp) * stepY).toFloat()

                    // Circles
                    drawCircle(Color.Blue, 6f, center = Offset(x, yMax))
                    drawCircle(Color.Cyan, 6f, center = Offset(x, yMin))

                    // Connecting lines
                    if (index > 0) {
                        val prevX = (index - 1) * stepX.toFloat()
                        val prevYMax =
                            size.height - ((daily[index - 1].maxTemp - minTemp) * stepY).toFloat()
                        val prevYMin =
                            size.height - ((daily[index - 1].minTemp - minTemp) * stepY).toFloat()

                        drawLine(Color.Blue, Offset(prevX, prevYMax), Offset(x, yMax), 2f)
                        drawLine(Color.Cyan, Offset(prevX, prevYMin), Offset(x, yMin), 2f)
                    }

                    // Temp labels
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${day.maxTemp}°",
                            x,
                            yMax - 24,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 32f
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                        )
                        drawText(
                            "${day.minTemp}°",
                            x,
                            yMin + 48,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.GRAY
                                textSize = 32f
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // ---- Common Icon + Wind ----
        Row(
            modifier = Modifier.width(cellWidth * daily.size),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daily.forEach { day ->
                Column(
                    modifier = Modifier.width(cellWidth),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle, // same icon for all
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${day.windSpeed} km/h",
                        fontSize = 11.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }   }

