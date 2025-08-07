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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myweather.data.model.WeatherCondition
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
    apiViewModel: ApiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val location by locationViewModel.location.collectAsState()

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    var permissionRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!permissionRequested) {
            permissionRequested = true
            locationPermissionState.launchMultiplePermissionRequest()
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
                // Still loading location
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        // User Denied → fallback to Search
        locationPermissionState.shouldShowRationale || !locationPermissionState.allPermissionsGranted -> {
            SearchScreen(
                viewModel = apiViewModel,
                onSearch = { city ->
                    apiViewModel.getData(city)
                }
            )
        }
    }
}
