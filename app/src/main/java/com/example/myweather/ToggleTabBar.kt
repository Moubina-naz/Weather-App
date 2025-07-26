package com.example.myweather

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

@Composable
fun DayChooser(
                     modifier: Modifier = Modifier){
    val options = listOf("Today", "Tomorrow", "6 days")
    val selectedOption = "Daily"

    Row(
        modifier = modifier.fillMaxWidth()
            .background(Color(0xFFEAE4CA))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            val bgColor = if (isSelected) Color(0xFF8AD4D4) else Color(0xFFEAE4CA)
            val textColor = if (isSelected) Color.White else Color.Black
            val borderColor = if (isSelected) Color.Transparent else Color(0xFFEAE4CA)

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(bgColor)
                    .border(width = 0.dp,
                        color = bgColor,
                        shape = RoundedCornerShape(20.dp)
                    ).clickable {  }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = option,
                    color = textColor,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DayChooserPreview() {
    DayChooser()
}