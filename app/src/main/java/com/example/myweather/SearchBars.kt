package com.example.myweather

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.API.NetworkResponse

@Composable
fun SearchBars(modifier: Modifier = Modifier,
               viewModel: WeatherViewModel,onSearch: (String) -> Unit){

    var city by remember { mutableStateOf("") }

    val weatherResult = viewModel.weatherResult.observeAsState()

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
                }}
                ,
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = {
                Text("Search...", fontSize = 18.sp)
            },

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFf6f0dd),
                unfocusedBorderColor = Color(0xFFf6f0dd),
                cursorColor = Color.Black,
                focusedTextColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
        )

}