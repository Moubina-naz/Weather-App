import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myweather.R
import com.example.myweather.data.model.SearchScrn
import com.example.myweather.ui.screen.SearchScreen
import com.example.myweather.ui.screen.WeatherScreen
import com.example.myweather.viewmodel.ApiViewModel
import com.example.myweather.viewmodel.StartupViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartupScreen(
    locationViewModel: StartupViewModel,navController:  NavController,
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
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                locationViewModel.fetchUserLocation()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF7ECD6)), ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,) {
                        Image(
                            painter = painterResource(id = R.drawable.startup),
                            contentDescription = "Weather Icon",
                            modifier = Modifier
                                .fillMaxWidth() ,         // Full width
                              //  .align(Alignment.TopStart), // Center the image
                            contentScale = ContentScale.FillWidth // Stretch to fill width, preserve aspect
                        )


                        Spacer(modifier = Modifier.height(40.dp))
                        Image(painter = painterResource(id = R.drawable.location), contentDescription = "", modifier = Modifier.size(120.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Enable Location", fontSize = 40.sp,fontWeight = FontWeight.SemiBold,style = MaterialTheme.typography.labelLarge)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Get accurate forcast for your exact location" ,fontSize = 20.sp, style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1C484C),
                            contentColor = Color.White
                        )
                        ) {
                            Text("Enable",fontSize = 20.sp, style = MaterialTheme.typography.labelSmall)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        var isClicked by remember { mutableStateOf(false) }
                        Text(
                            text = "Set Location Manually",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1C484C),
                            ),
                            modifier = Modifier.clickable { isClicked = !isClicked
                            navController.navigate(SearchScrn) })


                    }
                }

            }
            }


        // User Denied → fallback to Search
    }
}

