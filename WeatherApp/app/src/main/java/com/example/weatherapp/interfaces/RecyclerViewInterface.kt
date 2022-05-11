package com.example.weatherapp.interfaces

import com.example.weatherapp.models.WeatherCardModel

interface RecyclerViewInterface {

    fun onItemClick(position: Int, item: WeatherCardModel)
    fun onAddItem()
    fun onLongClick(position: Int, item: WeatherCardModel)
}