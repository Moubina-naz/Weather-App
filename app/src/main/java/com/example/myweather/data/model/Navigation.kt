package com.example.myweather.data.model

import StartupScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.myweather.ui.components.ForecastUI
import com.example.myweather.ui.screen.SearchScreen
import com.example.myweather.ui.screen.WeatherScreen
import com.example.myweather.viewmodel.ApiViewModel
import com.example.myweather.viewmodel.StartupViewModel
import kotlinx.serialization.Serializable

@Serializable
object Startup
@Serializable
object SearchScrn
@Serializable
object WeatherScrn
@Serializable
data class Forecastui(val cityName: String)
@Serializable
data class CityScrn(val cityName: String)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    viewModel: ApiViewModel,
    locationViewModel: StartupViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Startup){
composable<Startup>{
StartupScreen(locationViewModel = locationViewModel, apiViewModel = viewModel, navController = navController)
}
        composable<SearchScrn>{
            SearchScreen(viewModel = viewModel, navController = navController,)
        }
        composable<WeatherScrn>{
            WeatherScreen(viewModel = viewModel)
        }
        composable<Forecastui>{ backStackEntry->
            val cityName = backStackEntry.toRoute<Forecastui>().cityName
            ForecastUI(
                viewModel = viewModel,
                navController = navController,
                city = cityName )
        }
    }
}