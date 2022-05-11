package com.example.weatherapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.R

class customToast {
    companion object {
        fun showErrorToast(context: Context, message: String) {
            val parent: ViewGroup? = null
            val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val toastView = inflater.inflate(R.layout.custom_error_toast, parent)
            toastView.findViewById<TextView>(R.id.errorMessage).text = message
            toast.view = toastView
            toast.show()
        }
    }
}