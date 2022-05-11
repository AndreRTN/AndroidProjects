package com.example.recyclermasterkotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mooveit.library.Fakeit
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {


    private val _email: MutableLiveData<Email> = MutableLiveData()

    companion object {
        private lateinit var fakeit: Fakeit
        fun getInstance(): Fakeit {
            if (!::fakeit.isInitialized)
                fakeit = Fakeit.init()

            return fakeit
        }
    }

    val email = _email

    fun addEmail() {

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).parse(
            Fakeit.dateTime().dateFormatter()
        )
        val stringBuilder = StringBuilder()

        for (i in 1..10) {
            stringBuilder.append(Fakeit.lorem().words())
            stringBuilder.append(" ")
        }

        val email = Email(
            user = Fakeit.name().firstName(),
            subject = Fakeit.company().name(),
            date = SimpleDateFormat("d MMM", Locale("pt", "BR")).format(sdf!!),
            preview = stringBuilder.toString(),
            stared = false,
            unread = true
        )

        _email.value = email
    }

}