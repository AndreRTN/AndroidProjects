package com.example.weatherapp.dtos

import com.google.gson.annotations.SerializedName

data class ErrorDTO(
    @SerializedName("message")
    val message: String
)