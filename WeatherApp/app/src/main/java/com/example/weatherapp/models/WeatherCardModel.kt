package com.example.weatherapp.models

import com.example.weatherapp.enums.WeatherType

data class WeatherCardModel(
    val viewType: Int = 0,
    val icon: Int?,
    val temp: Long?,
    var city: String?,
    var background: WeatherType?,
    var isFavorite: Boolean = false

)

