package com.example.weatherapp.fragments

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.interfaces.RecyclerViewInterface
import com.example.weatherapp.models.WeatherCardModel
import com.example.weatherapp.utils.customToast
import com.example.weatherapp.view.adapters.WeatherAdapter
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator


class FavoritesFragment : Fragment(), RecyclerViewInterface {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var cityManagementText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.city_list, container, false)

        searchView = view.findViewById(R.id.search_view)
        searchView.visibility = View.GONE

        cityManagementText = view.findViewById<TextView?>(R.id.tv_city_management).apply {
            text = "Favorites"
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_cities)
        val gridLayoutmanager: GridLayoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutmanager
        weatherAdapter = mainViewModel.list.value.let {
            Log.i("size info before", it?.size.toString())
            WeatherAdapter(it!!.filter { weatherCardModel -> weatherCardModel.isFavorite }, this)

        }
        recyclerView.adapter = weatherAdapter

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_cities)
        val gridLayoutmanager: GridLayoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView?.layoutManager = gridLayoutmanager
        weatherAdapter = mainViewModel.list.value.let {
            Log.i("size info before", it?.size.toString())
            WeatherAdapter(it!!, this)

        }
        recyclerView?.adapter = weatherAdapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, item: WeatherCardModel) {
        Log.i("card position", position.toString())
        Log.i("card data", item.toString())
        mainViewModel.addItemDetail(item)

        findNavController().navigate(R.id.cityDetailsFragment)

    }

    override fun onAddItem() {
        showDialog()
    }

    override fun onLongClick(position: Int, item: WeatherCardModel) {
        showDialog()
    }

    private fun showDialog() {
        val dialog = AddDialogFragment(
            object : AddDialogFragment.MyDialogListener {
                override fun onOkClicked(dialog: View) {
                    val textCity =
                        dialog.findViewById<TextView>(R.id.text_input_city).text.toString()
                    mainViewModel.getCityCurrentWeather(textCity)
                }

            }
        )
        dialog.show(childFragmentManager, "ADD")
    }


}