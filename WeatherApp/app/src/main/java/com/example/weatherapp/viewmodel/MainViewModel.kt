package com.example.weatherapp.viewmodel


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.R
import com.example.weatherapp.dtos.WeatherDTO
import com.example.weatherapp.enums.WeatherType
import com.example.weatherapp.http.RetrofitClient
import com.example.weatherapp.models.UpComing
import com.example.weatherapp.models.WeatherCardModel
import com.example.weatherapp.models.WeatherDetailModel
import com.example.weatherapp.services.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask


class MainViewModel : ViewModel() {

    private val weatherDetailModel: MutableLiveData<WeatherDetailModel> = MutableLiveData()
    private val listWeatherDTO: MutableList<WeatherDTO> = ArrayList()
    private val remote = RetrofitClient.createService(WeatherService::class.java)
    private val _itemToAdd: MutableLiveData<WeatherCardModel> = MutableLiveData()
    val showErrorToast: MutableLiveData<String> = MutableLiveData()
    val hasLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val mockList: MutableLiveData<List<WeatherCardModel>> =
        MutableLiveData<List<WeatherCardModel>>().apply {
            value = arrayListOf(

                WeatherCardModel(
                    viewType = 1,
                    null,
                    null,
                    null,
                    null
                )

            )
        }

    val list = mockList
    val itemToAdd = _itemToAdd
    val weatherDetail = weatherDetailModel
    private val mutableList = mockList.value?.toMutableList()
    fun getCityCurrentWeather(city: String) {
        hasLoading.value = true
        var weatherDTO: WeatherDTO

        val call: Call<WeatherDTO> = remote.getCurrent(city)

        call.enqueue(object : Callback<WeatherDTO> {
            override fun onResponse(
                call: Call<WeatherDTO>,
                response: Response<WeatherDTO>
            ) {
                if (response.code() != 400) {
                    weatherDTO = response.body() as WeatherDTO

                    val weatherCardModel = processWeatherData(weatherDTO)
                    mutableList?.add(weatherCardModel)
                    listWeatherDTO.add(weatherDTO)
                    itemToAdd.value = weatherCardModel
                    mockList.value = mutableList
                    Log.i("city", weatherDTO.toString())
                }
                if (response.code() >= 400) {
                    showErrorToast.value = ("City not found")
                }

                hasLoading.value = false
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                Log.e("Error", t.message.toString())
                hasLoading.value = false
            }

        })


    }

    fun removeCardCity(position: Int) {
           val newList = mockList.value?.toMutableList()
        newList?.removeAt(position)
        mockList.value = newList

    }

    fun addToFavorites(weatherCardModel: WeatherCardModel) {
        mutableList?.find {
            it.city == weatherCardModel.city
        }?.isFavorite = true

    }

    fun removeToFavorites(weatherCardModel: WeatherCardModel) {
        mutableList?.find {
            it.city == weatherCardModel.city
        }?.isFavorite = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addItemDetail(weatherCardModel: WeatherCardModel) {
        val decimalFormat = DecimalFormat("00").apply {
            roundingMode = RoundingMode.DOWN
        }
        val nowDate = LocalDate.now(ZoneId.systemDefault())
        val calendar = Calendar.getInstance()
        val dayOfWeek = nowDate.dayOfWeek.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val dayOfMonth = nowDate.month.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val month = decimalFormat.format(Calendar.MONTH + 1)
        val year = calendar.get(Calendar.YEAR)
        val stringDate = "$dayOfWeek | $month $dayOfMonth $year"

        val foundWeatherCard = listWeatherDTO.first {
            it.location.name == weatherCardModel.city
        }

        val upComings: MutableList<UpComing> = ArrayList()

        foundWeatherCard.forecast.forecastday.forEach {
            val dayOfWeekUpcoming = LocalDate.parse(it.date).dayOfWeek.name
            val mediumTemp = (it.day.maxtemp_c + it.day.mintemp_c) / 2
            val icon = defineUpComingIcon(
                it.day.daily_will_it_rain,
                it.day.maxwind_kph,
                it.day.daily_chance_of_rain
            )
            upComings.add(
                UpComing(
                    "${mediumTemp.toInt()} ° C",
                    icon,
                    dayOfWeekUpcoming.substring(0, 3)
                )
            )
        }

        val weatherDetail = WeatherDetailModel(
            weatherCardModel.city.toString(),
            foundWeatherCard.current.condition.text,
            weatherCardModel.temp.toString() + "° C",
            weatherCardModel.icon!!,
            stringDate,
            upComings,
            isFavorite = weatherCardModel.isFavorite
        )

        weatherDetailModel.value = weatherDetail
    }

    private fun defineUpComingIcon(
        dailyWillItRain: Double,
        maxwindKph: Double,
        chanceOfRain: Double
    ): Int {

        return when {
            maxwindKph >= 19 -> R.drawable.ic_wind_upcoming
            dailyWillItRain.toInt() == 1 && chanceOfRain > 80 -> R.drawable.ic_weather_upcoming
            dailyWillItRain.toInt() == 0 && maxwindKph < 19 -> R.drawable.ic_sun_upcoming

            else -> R.drawable.ic_sun_upcoming
        }
    }

    fun processWeatherData(weatherDTO: WeatherDTO): WeatherCardModel {
        val weatherCardModel: WeatherCardModel = weatherDTO.let {
            val background: WeatherType
            val icon: Int
            when (it.current.is_day) {
                0 -> {
                    if (it.current.condition.code >= 1030) {
                        icon = R.drawable.ic_cloudy_night
                        background = WeatherType.CLOUDY_NIGHT
                    } else {
                        icon = R.drawable.ic_night
                        background = WeatherType.NIGHT
                    }
                }
                1 -> {
                    if (it.current.condition.code >= 1030) {
                        icon = R.drawable.ic_rain
                        background = WeatherType.RAIN
                    } else {
                        icon = R.drawable.ic_cloudy_day
                        background = WeatherType.CLOUDY_DAY
                    }
                }
                else -> {
                    icon = R.drawable.ic_snow
                    background = WeatherType.SNOW
                }

            }

            WeatherCardModel(0, icon, it.current.temp_c.toLong(), it.location.name, background)
        }
        return weatherCardModel

    }

}