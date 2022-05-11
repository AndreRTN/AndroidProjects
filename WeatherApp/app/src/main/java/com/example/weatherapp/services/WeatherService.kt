package com.example.weatherapp.services

import com.example.weatherapp.dtos.WeatherDTO
import com.example.weatherapp.models.WeatherCardModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("forecast.json")
    fun getCurrent(
        @Query("q") city: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"

    ): Call<WeatherDTO>
}