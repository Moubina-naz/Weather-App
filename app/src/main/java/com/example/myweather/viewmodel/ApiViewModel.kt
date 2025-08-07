package com.example.myweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.data.api.Constant
import com.example.myweather.data.api.NetworkResponse
import com.example.myweather.data.api.RetrofitInstance
import com.example.myweather.data.model.CitySearchResponse
import com.example.myweather.data.model.WeatherResponse
import kotlinx.coroutines.launch

class ApiViewModel: ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherResponse>>()
    val weatherResult: LiveData<NetworkResponse<WeatherResponse>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeatherByCity(city, Constant.apikey)

                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Code: ${response.code()}, Error: $errorBody")
                    _weatherResult.postValue(NetworkResponse.Error("API Error: ${response.code()}"))
                }


                val weatherResponse = response.body() ?: run {
                    _weatherResult.postValue(
                        NetworkResponse.Error("Empty response from server")
                    )
                    return@launch
                }

                // Additional validation for critical fields
                if (weatherResponse.list.isNullOrEmpty() || weatherResponse.city == null) {
                    _weatherResult.postValue(
                        NetworkResponse.Error("Incomplete weather data received")
                    )
                    return@launch
                }

                _weatherResult.postValue(NetworkResponse.Success(weatherResponse))

            } catch (e: Exception) {
                _weatherResult.postValue(
                    NetworkResponse.Error(e.message ?: "Network request failed")
                )
            }
        }
    }

    fun getDataByLocation(lat: Double, lon: Double) {
        _weatherResult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeatherByCoords(lat, lon, Constant.apikey)

                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Code: ${response.code()}, Error: $errorBody")
                    _weatherResult.postValue(NetworkResponse.Error("API Error: ${response.code()}"))
                    return@launch
                }

                val weatherResponse = response.body() ?: run {
                    _weatherResult.postValue(NetworkResponse.Error("Empty response from server"))
                    return@launch
                }

                if (weatherResponse.list.isNullOrEmpty() || weatherResponse.city == null) {
                    _weatherResult.postValue(NetworkResponse.Error("Incomplete weather data received"))
                    return@launch
                }

                _weatherResult.postValue(NetworkResponse.Success(weatherResponse))
            } catch (e: Exception) {
                _weatherResult.postValue(NetworkResponse.Error(e.message ?: "Network request failed"))
            }
        }
    }

    private val _searchResults = MutableLiveData<NetworkResponse<List<CitySearchResponse>>>()
    val searchResults: LiveData<NetworkResponse<List<CitySearchResponse>>> = _searchResults


    fun SearchCity(query: String){
        _searchResults.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                val response = weatherApi.searchCity(query, 5, Constant.apikey)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body.isNullOrEmpty()) {
                        _searchResults.postValue(NetworkResponse.Error("No results found"))
                    } else {
                        _searchResults.postValue(NetworkResponse.Success(body))
                    }
                } else {
                    _searchResults.postValue(NetworkResponse.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                _searchResults.postValue(NetworkResponse.Error("Exception: ${e.message}"))
            }
        }
    }


    }
