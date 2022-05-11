package com.example.weatherapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.enums.WeatherType
import com.example.weatherapp.interfaces.RecyclerViewInterface
import com.example.weatherapp.models.WeatherCardModel
import com.google.android.material.transition.Hold
import java.util.*

class WeatherAdapter(
    private var list: List<WeatherCardModel>,
    private val listener: RecyclerViewInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var weatherCardList = list.toMutableList()

    private inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherCardModel: WeatherCardModel) {
            val weatherType = itemView.findViewById<ImageView>(R.id.iv_type_weather)
            val tempNumber = itemView.findViewById<TextView>(R.id.tv_temp_number)
            val city = itemView.findViewById<TextView>(R.id.tv_city)
            val backgroundLayout: LinearLayout = itemView.findViewById(R.id.background_weather)
            setWeatherBackground(weatherCardModel.background!!, backgroundLayout)
            weatherType.setImageResource(weatherCardModel.icon!!)
            tempNumber.text = weatherCardModel.temp.toString() + "° C"
            city.text = weatherCardModel.city
        }

        fun onClickCity(weatherCardModel: WeatherCardModel) {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, weatherCardModel)
            }


        }


        fun onClickAddCity() {
            itemView.setOnClickListener {
                listener.onAddItem()
            }
        }

        fun setWeatherBackground(type: WeatherType, layout: LinearLayout) {
            when (type) {
                WeatherType.CLOUDY_DAY -> layout.setBackgroundResource(R.drawable.sun_gradient)
                WeatherType.CLOUDY_NIGHT -> layout.setBackgroundResource(R.drawable.cloudy_night_gradient)
                WeatherType.NIGHT -> layout.setBackgroundResource(R.drawable.night_gradient)
                WeatherType.SNOW -> layout.setBackgroundResource(R.drawable.snow_gradient)
                WeatherType.RAIN -> layout.setBackgroundResource(R.drawable.rain_gradient)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder


        when (viewType) {
            0 -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)

                viewHolder = WeatherViewHolder(view)
            }
            1 -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.edit_city, parent, false)
                viewHolder = WeatherViewHolder(view)
            }
        }
        return viewHolder

    }

    override fun getItemViewType(position: Int): Int {
        return weatherCardList[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (weatherCardList[position].viewType == 0) {
            (holder as WeatherViewHolder).bind(weatherCardList[position])
            holder.onClickCity(weatherCardList[position])
        } else {
            (holder as WeatherViewHolder).onClickAddCity()
        }
    }

    override fun getItemCount(): Int {
        return weatherCardList.size
    }

    fun search(query: String): Boolean {
        val currentSze = weatherCardList.size
        weatherCardList.clear()

        val filteredItems = list.filter {
            it.let {
                it.city?.contains(query, true) == true || it.viewType == 1 ?: false
            }
        }
        weatherCardList.addAll(filteredItems)

        notifyItemRangeRemoved(0, currentSze)

        notifyItemRangeInserted(0, filteredItems.size)

        return weatherCardList.isEmpty()
    }

    fun addItem(weatherCardModel: WeatherCardModel) {
        weatherCardList.add(weatherCardModel)
        val newList = list.toMutableList()
        newList.add(weatherCardModel)
        list = newList
        notifyItemInserted(weatherCardList.size)
    }

    fun removeItem(weatherCardModel: WeatherCardModel) {
        val currentPosition = weatherCardList.indexOf(weatherCardModel)
        weatherCardList.remove(weatherCardModel)
        notifyItemRemoved(currentPosition)
    }

    fun swipeItems(from: Int, to: Int) {
        println("swiping...")
        Collections.swap(weatherCardList, from, to)
        notifyItemMoved(from, to)
    }

    fun clearSearch() {
        weatherCardList = list.toMutableList()
        notifyDataSetChanged()
    }
}