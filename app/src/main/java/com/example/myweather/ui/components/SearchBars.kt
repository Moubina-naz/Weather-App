package com.example.myweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.data.model.WeatherCondition
import com.example.myweather.ui.theme.getWeatherPalette
import com.example.myweather.viewmodel.WeatherViewModel

@Composable
fun SearchBars(modifier: Modifier = Modifier, weatherCondition: WeatherCondition,
               viewModel: WeatherViewModel, onSearch: (String) -> Unit) {

    var city by remember { mutableStateOf("") }
    val palette = getWeatherPalette(weatherCondition)
    val weatherResult = viewModel.weatherResult.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(elevation = 6.dp, shape = RectangleShape, clip = false) // 🌟 adds floating shadow
            .background(palette.searchbg)
    ) {

    OutlinedTextField(
        value = city,
        onValueChange = {
            city = it
        },
        leadingIcon = {
        },
        trailingIcon = {
            androidx.compose.material3.IconButton(
                onClick = {
                    if (city.isNotBlank()) {
                        viewModel.getData(city.trim())  // This is the key trigger
                        onSearch(city)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        textStyle = TextStyle(fontSize = 18.sp),
        placeholder = {
            Text("Search...", fontSize = 18.sp)
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = palette.searchbg,
            unfocusedContainerColor = palette.searchbg,
            focusedTextColor = palette.searchtext,
            unfocusedTextColor = palette.searchtext,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Red
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(palette.searchbg)
            .height(56.dp)
    )
}

}