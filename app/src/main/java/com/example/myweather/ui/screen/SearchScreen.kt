package com.example.myweather.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.data.api.NetworkResponse
import com.example.myweather.data.model.WeatherScrn
import com.example.myweather.ui.components.SearchBars
import com.example.myweather.viewmodel.ApiViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier,navController: NavController,
                 viewModel: ApiViewModel,) {

    val searchResults by viewModel.searchResults.observeAsState()

    Box(modifier = Modifier.background(Color(0xFF1B1C1E))){
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1B1C1E))) {
            Spacer(modifier = Modifier.height(40.dp))
            SearchBars(viewModel = viewModel,
                onSearch = { city ->
                    viewModel.getData(city) // this triggers the API call
                    navController.navigate(WeatherScrn)
                })
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1B1C1E))
            ) {
                when (val result = searchResults) {
                    is NetworkResponse.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .wrapContentWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = "Searching...",
                                    fontSize = 20.sp, style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }

                    is NetworkResponse.Success -> {
                        val suggestions = result.data
                        items(suggestions.size) { index ->
                            val city = suggestions[index]
                            val statePart = city.state?.let { ", $it" } ?: ""
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .wrapContentWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF1B1C1E))
                            ) {
                                Text(
                                    text = "${city.name}, ${city.country}",
                                    fontSize = 20.sp, style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth()
                                        .background(Color(0xFF1B1C1E))
                                        .padding(8.dp)
                                )
                            }

                        }
                    }

                    is NetworkResponse.Error -> {
                        item {
                            Text(
                                text = "Error: ${result.message}",
                                modifier = Modifier.padding(16.dp),
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.labelSmall,
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

}