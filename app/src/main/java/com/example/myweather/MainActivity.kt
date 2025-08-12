package com.example.myweather

import StartupScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myweather.data.model.Navigation
import com.example.myweather.ui.screen.WeatherScreen
import com.example.myweather.ui.theme.MyWeatherTheme
import com.example.myweather.viewmodel.ApiViewModel
import com.example.myweather.viewmodel.StartupViewModel
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: ApiViewModel = viewModel()
                    val context = LocalContext.current
                    val locationManager = remember {
                        LocationManager(
                            context = context,
                            fusedClient = LocationServices.getFusedLocationProviderClient(context)
                        )
                    }
                    val startupViewModel = remember {
                        StartupViewModel(locationManager)
                    }
                    Navigation(
                        viewModel = viewModel,
                        locationViewModel = startupViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherTheme {
        Greeting("Android")
    }
}