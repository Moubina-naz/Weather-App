package com.example.myweather


import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager as AndroidLocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationManager(
    private val context: Context,
    private val fusedClient: FusedLocationProviderClient
) {

    suspend fun getLocation(): Location? {
        val fineGranted = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationService = context.getSystemService(Context.LOCATION_SERVICE) as AndroidLocationManager
        val gpsEnabled = locationService.isProviderEnabled(AndroidLocationManager.GPS_PROVIDER) ||
                locationService.isProviderEnabled(AndroidLocationManager.NETWORK_PROVIDER)

        if (!gpsEnabled || (!fineGranted && !coarseGranted)) return null

        return suspendCancellableCoroutine { cont ->
            fusedClient.lastLocation.apply {
                if (isComplete) {
                    cont.resume(if (isSuccessful) result else null)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { cont.resume(it) }
                addOnFailureListener { cont.resume(null) }
                addOnCanceledListener { cont.cancel() }
            }
        }
    }
}
