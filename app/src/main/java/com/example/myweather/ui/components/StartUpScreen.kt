import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myweather.ui.components.SearchScreen
import com.example.myweather.ui.screen.WeatherScreen
import com.example.myweather.viewmodel.ApiViewModel
import com.example.myweather.viewmodel.StartupViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartupScreen(
    locationViewModel: StartupViewModel,
    apiViewModel: ApiViewModel // You already have this
) {
    val location by locationViewModel.location.collectAsState()
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (locationPermissionState.allPermissionsGranted) {
            locationViewModel.fetchUserLocation()
        }
    }

    when {
        locationPermissionState.allPermissionsGranted -> {
            if (location != null) {
                val lat = location!!.latitude
                val lon = location!!.longitude
                LaunchedEffect(lat, lon) {
                    apiViewModel.getDataByLocation(lat, lon)
                }
                WeatherScreen(viewModel = apiViewModel)
            } else {
                // Waiting for location
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        locationPermissionState.shouldShowRationale || !locationPermissionState.allPermissionsGranted -> {
            // Ask permission
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("We need your location to show the weather.")
                Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                    Text("Grant Permission")
                }
                Spacer(Modifier.height(16.dp))
                SearchScreen(viewModel = apiViewModel) // fallback UI
            }
        }
    }
}
