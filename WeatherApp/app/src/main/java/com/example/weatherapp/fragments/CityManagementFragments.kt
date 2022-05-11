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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.interfaces.RecyclerViewInterface
import com.example.weatherapp.models.WeatherCardModel
import com.example.weatherapp.utils.customToast
import com.example.weatherapp.view.adapters.WeatherAdapter
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator


class CityManagementFragments : Fragment(), RecyclerViewInterface {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var circularProgressIndicator: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.city_list, container, false)



        searchView = view.findViewById(R.id.search_view)
        circularProgressIndicator = view.findViewById(R.id.circular_progress)
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            val textView = view.findViewById<TextView>(R.id.tv_city_management)!!
            if (hasFocus) {
                textView.visibility = View.INVISIBLE
            } else {
                searchView.onActionViewCollapsed()
                textView.visibility = View.VISIBLE
            }

        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_cities)
        val gridLayoutmanager: GridLayoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutmanager
        weatherAdapter = mainViewModel.list.value.let {
            Log.i("size info before", it?.size.toString())
            WeatherAdapter(it!!, this)

        }
        recyclerView.adapter = weatherAdapter
        val itemTouchHelper = ItemTouchHelper(ItemTouchHandler())
        itemTouchHelper.attachToRecyclerView(recyclerView)
        searchViewListener()


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

        mainViewModel.itemToAdd.observe(requireActivity()) {
            if (it != null) {
                weatherAdapter.addItem(it)

            }
        }

        mainViewModel.hasLoading.observe(requireActivity()) {

            if (it == true) {
                circularProgressIndicator.visibility = View.VISIBLE
            } else {
                circularProgressIndicator.visibility = View.GONE
            }
        }

        mainViewModel.showErrorToast.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                customToast.showErrorToast(requireContext(), it)
            }

        }

        mainViewModel.showErrorToast.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                customToast.showErrorToast(requireContext(), it)
            }

        }
    }

    private fun searchViewListener() {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                weatherAdapter.search(query.toString())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (searchView.hasFocus()) weatherAdapter.search(query.toString())
                return false
            }
        })
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, item: WeatherCardModel) {
        Log.i("card position", position.toString())
        Log.i("card data", item.toString())
        mainViewModel.addItemDetail(item)

        findNavController().navigate(R.id.action_cityManagementFragments_to_cityDetailsFragment)

    }


    override fun onAddItem() {
        showDialog()
    }

    override fun onLongClick(position: Int, item: WeatherCardModel) {
        showDeleteCardDialog(position, item)
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

    private fun showDeleteCardDialog(position: Int, item: WeatherCardModel) {
        val dialog = DeleteCardDialogFragment(
            object : DeleteCardDialogFragment.MyDialogListener {
                override fun onOkClicked() {
                    mainViewModel.removeCardCity(position)
                    weatherAdapter.removeItem(item)
                }
            }

        )

        dialog.show(childFragmentManager, "REMOVE")
    }

    inner class ItemTouchHandler :
        ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeFlag(
                ItemTouchHelper.ACTION_STATE_DRAG,
                ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            println("active on move")
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            weatherAdapter.swipeItems(from, to)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }

}