package com.example.myweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.myweather.data.api.NetworkResponse
import com.example.myweather.data.model.WeatherCondition
import com.example.myweather.ui.theme.getWeatherPalette
import com.example.myweather.viewmodel.ApiViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier,
                 viewModel: ApiViewModel, onSearch: (String) -> Unit) {

    val searchResults by viewModel.searchResults.observeAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
    SearchBars(viewModel = viewModel,
        onSearch = { city ->
            viewModel.getData(city) // this triggers the API call
                  // (optional) to show more data
        })
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            when (val result = searchResults) {
                is NetworkResponse.Loading -> {
                    item {
                        Text(
                            text = "Searching...",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is NetworkResponse.Success -> {
                    val suggestions = result.data
                    items(suggestions.size) { index ->
                        val city = suggestions[index]
                        Text(
                            text = "${city.name}, ${city.country}",
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                                .background(Color(0xFFEFEFEF))
                                .padding(8.dp)
                        )
                    }
                }

                is NetworkResponse.Error -> {
                    item {
                        Text(
                            text = "Error: ${result.message}",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Red
                        )
                    }
                }

                null -> {
                    // Do nothing
                }
            }
        }

    }

}