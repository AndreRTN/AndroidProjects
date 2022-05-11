package com.example.calcuulator

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var tvInput: TextView;
    private lateinit var moon: ImageView;
    private  lateinit var tvResult: TextView
    private lateinit var sun: ImageView;
    private lateinit var model: MainViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appSettingsPrefs: SharedPreferences = getSharedPreferences("AppSettingsPrefs", 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingsPrefs.edit()
        val nightModeOn: Boolean = appSettingsPrefs.getBoolean("NightMode", false)
        tvInput = findViewById(R.id.tv_result)
        tvResult = findViewById(R.id.tv_resultText)
        moon = findViewById(R.id.moon)
        sun = findViewById(R.id.sun)

        if (nightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            window.statusBarColor = ContextCompat.getColor(this, R.color.purple_500)

            window.navigationBarColor = ContextCompat.getColor(this, R.color.purple_500)

            activeMoon()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            activeSun()

            window.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.purple_200)
        }

        model =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(MainViewModel::class.java)
        model.result.observe(this) { value ->

            tvInput.text = value
        }

        model.resultText.observe(this){
            value -> tvResult.text = "= $value"
        }

        supportActionBar?.hide()

        sun.setOnClickListener {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefsEdit.putBoolean("NightMode", false)

            sharedPrefsEdit.apply()
        }

        moon.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefsEdit.putBoolean("NightMode", true)

            sharedPrefsEdit.apply()

        }

    }

    fun onDigit(view: View) {

        when (view.id) {
            R.id.btn_return -> model.returnDigit()
            R.id.btn_ac -> model.onClear()
            else -> model.onDigit((view as Button).text.toString())
        }

    }

    fun onDecimalPoint(view: View) {
        when (view.id) {
            R.id.btn_point -> model.onDecimalPoint()
            else -> model.onDecimalPoint()
        }
    }

    fun onOperator(view: View) {
        when (view.id) {
            R.id.btn_plus -> model.onOperator(Operators.SUM)
            R.id.btn_minus -> model.onOperator(Operators.SUBTRACT)
            R.id.btn_divide -> model.onOperator(Operators.DIVIDE)
            R.id.btn_multiply -> model.onOperator(Operators.MULTIPLY)
            else -> model.onOperator(Operators.EQUALS)
        }
    }

    fun activeSun() {

        val purple: Int = Color.parseColor("#FF6200EE")
        moon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        sun.setColorFilter(purple)
    }

    fun activeMoon() {
        val purple: Int = Color.parseColor("#FF6200EE")
        sun.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        moon.setColorFilter(purple)

    }

}