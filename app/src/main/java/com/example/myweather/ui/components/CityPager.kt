package com.example.myweather.ui.components

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.example.myweather.viewmodel.ApiViewModel

@Composable
fun CityPager(viewModel : ApiViewModel){
    val cities = viewModel.savedCities
    val pagerState = rememberPagerState( initialPage = 0,
        pageCount = { cities.size } )
}