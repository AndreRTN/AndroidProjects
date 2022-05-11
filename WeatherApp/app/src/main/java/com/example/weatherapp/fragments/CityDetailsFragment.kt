package com.example.weatherapp.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.enums.UpcomingEnum
import com.example.weatherapp.models.WeatherCardModel
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class CityDetailsFragment : Fragment() {


    private lateinit var mainViewModel: MainViewModel

    private lateinit var tvCity: TextView
    private lateinit var tvWeatherType: TextView
    private lateinit var tvWeatherTemp: TextView
    private lateinit var ivWeather: ImageView
    private lateinit var tvWeatherDate: TextView
    private lateinit var dropdownExpand: LinearLayout
    private lateinit var rvUpcoming: RelativeLayout
    private lateinit var ivBackHome: ImageView
    private lateinit var cvUpComing: CardView
    private lateinit var toggleFavorite: ToggleButton
    private lateinit var weatherCardModel: WeatherCardModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.city_details, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility =
            View.GONE


        toggleFavorite = view.findViewById(R.id.toggle_favorite)
        ivBackHome = view.findViewById(R.id.back_to_home)
        tvCity = view.findViewById(R.id.tv_city)
        tvWeatherType = view.findViewById(R.id.tv_weather_type)
        tvWeatherTemp = view.findViewById(R.id.tv_weather_temp)
        ivWeather = view.findViewById(R.id.iv_weather_icon)
        ivWeather = view.findViewById(R.id.iv_weather_icon)
        tvWeatherDate = view.findViewById(R.id.tv_weather_date)
        dropdownExpand = view.findViewById(R.id.iv_dropdown_expand)
        rvUpcoming = view.findViewById(R.id.rv_upcoming)
        cvUpComing = view.findViewById(R.id.cv_upcoming)

        mainViewModel.weatherDetail.value.let {
            it?.upcomings?.forEachIndexed { index, upComing ->

                when (index) {
                    UpcomingEnum.FIRST_UPCOMING.value -> {

                        view.findViewById<TextView>(R.id.upcoming_first_temp).text =
                            upComing.temp.toString()
                        view.findViewById<ImageView>(R.id.upcoming_first_icon)
                            .setImageResource(upComing.icon)
                        view.findViewById<TextView>(R.id.upcoming_first_day).text = upComing.day
                    }
                    UpcomingEnum.SECOND_UPCOMING.value -> {

                        view.findViewById<TextView>(R.id.upcoming_second_temp).text =
                            upComing.temp.toString()
                        view.findViewById<ImageView>(R.id.upcoming_second_icon)
                            .setImageResource(upComing.icon)
                        view.findViewById<TextView>(R.id.upcoming_second_day).text = upComing.day
                    }
                    UpcomingEnum.THIRD_UPCOMING.value -> {

                        view.findViewById<TextView>(R.id.upcoming_third_temp).text =
                            upComing.temp.toString()
                        view.findViewById<ImageView>(R.id.upcoming_third_icon)
                            .setImageResource(upComing.icon)
                        view.findViewById<TextView>(R.id.upcoming_third_day).text = upComing.day

                    }
                }

            }
            toggleFavorite.isChecked = it?.isFavorite == true
            tvCity.text = it?.city
            tvWeatherType.text = it?.type
            tvWeatherTemp.text = it?.temp
            ivWeather.setImageResource(it!!.icon)
            tvWeatherDate.text = it.today
            dropdownExpand = view.findViewById(R.id.iv_dropdown_expand)
            rvUpcoming = view.findViewById(R.id.rv_upcoming)

            weatherCardModel = WeatherCardModel(
                viewType = 0,
                city = it.city,
                background = null,
                icon = null,
                temp = null
            )
        }
        addListeners()

        // Inflate the layout for this fragment
        return view
    }

    override fun onDetach() {
        super.onDetach()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility =
            View.VISIBLE

    }

    private fun addListeners() {

        toggleFavorite.setOnCheckedChangeListener { _, isChecked ->


            if(isChecked) {
                mainViewModel.addToFavorites(weatherCardModel)
            } else {
                mainViewModel.removeToFavorites(weatherCardModel)
            }

        }

        dropdownExpand.setOnClickListener {
            val shortAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)

            if (cvUpComing.visibility == View.VISIBLE) {
                cvUpComing.apply {
                    alpha = 1f
                    visibility = View.GONE
                    animate()
                        .alpha(0f)
                        .setDuration(shortAnimationDuration.toLong())
                        .setListener(null)
                }
            } else {
                cvUpComing.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate()
                        .alpha(1f)
                        .setDuration(shortAnimationDuration.toLong())
                        .setListener(null)
                }
            }
        }

        ivBackHome.setOnClickListener {
            findNavController().popBackStack( )
        }
    }


}