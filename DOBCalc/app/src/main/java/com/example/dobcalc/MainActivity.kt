package com.example.dobcalc

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.example.dobcalc.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var tvSelectedDate: TextView
    private lateinit var ageDate: TextView
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ageDate = binding.tvAge
        tvSelectedDate = binding.tv4

        val btnDatePicker = binding.btnSelectDate
        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }

    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(

            this,
            AlertDialog.THEME_HOLO_LIGHT,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, dayOfMonth ->

                val selectedDate = "$dayOfMonth/$selectedMonth/$selectedYear"

                tvSelectedDate.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val teste = Calendar.getInstance().get(Calendar.YEAR)
                val age = year - selectedYear

                ageDate.text = age.toString()
            },
            year, month, day
        )
        dpd.datePicker.maxDate = Date().time
        dpd.show()
        Toast.makeText(this, "btnDatePicker pressed", Toast.LENGTH_LONG).show()

    }
}