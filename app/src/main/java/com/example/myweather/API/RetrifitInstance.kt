package com.example.myweather.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrifitInstance {
private const val baseUrl="https://api.openweathermap.org/data/2.5/";
    private fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val weatherApi: ApiService = getInstance().create(ApiService::class.java)
    }
