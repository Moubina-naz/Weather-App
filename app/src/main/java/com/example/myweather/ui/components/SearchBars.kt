package com.example.myweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.myweather.viewmodel.ApiViewModel

@Composable
fun SearchBars(modifier: Modifier = Modifier,
               viewModel: ApiViewModel, onSearch: (String) -> Unit) {

    var city by remember { mutableStateOf("") }
    //val palette = getWeatherPalette(weatherCondition)
    val weatherResult = viewModel.weatherResult.observeAsState()

    val searchResults by viewModel.searchResults.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(elevation = 6.dp, shape = RectangleShape, clip = false)
            .background(Color(0xFF292A2C))
    ) {

    OutlinedTextField(
        value = city,
        onValueChange = {
            city = it
            if(city.length > 2){
                viewModel.SearchCity(city.trim())
            }
        },
        leadingIcon = {
        },
        trailingIcon = {
            androidx.compose.material3.IconButton(
                onClick = {
                    if (city.isNotBlank()) {
                        onSearch(city.trim())
                    }

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = Color.White,
                    contentDescription = "Search"
                )
            }
        },
        textStyle = TextStyle(fontSize = 18.sp),
        placeholder = {
            Text("Search...", fontSize = 18.sp)
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF292A2C),
            unfocusedContainerColor =Color(0xFF292A2C),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Red
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF292A2C))
            .height(56.dp)
    )
}

}