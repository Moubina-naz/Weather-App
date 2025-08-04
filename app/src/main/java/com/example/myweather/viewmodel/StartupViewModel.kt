package com.example.myweather.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StartupViewModel(app: Application) : AndroidViewModel(app) {

    private val locationManager = LocationManager(
        context = app.applicationContext,
        fusedClient = LocationServices.getFusedLocationProviderClient(app)
    )

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    fun fetchUserLocation() {
        viewModelScope.launch {
            val loc = locationManager.getLocation()
            _location.value = loc
        }
    }
}