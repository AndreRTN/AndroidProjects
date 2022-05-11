package com.example.weatherapp.dtos

import CurrentDTO
import ForecastDTO
import LocationDTO
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val location: LocationDTO,
    val current: CurrentDTO,
    val error: ErrorDTO,
    @SerializedName("forecast")
    val forecast: ForecastDTO
)