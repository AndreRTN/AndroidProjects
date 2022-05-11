package com.example.weatherapp.models

data class WeatherDetailModel(
    val city: String,
    val type: String,
    val temp: String,
    val icon: Int,
    val today: String,
    val upcomings: List<UpComing>,
    val isFavorite: Boolean = false
)