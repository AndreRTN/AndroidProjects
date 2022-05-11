package com.example.quizapp.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _error: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = null
    }
    val error: MutableLiveData<Boolean> = _error

    val name: MutableLiveData<String> = MutableLiveData()

    fun verifyName(text: String) {
        _error.value = text.isEmpty()

        _error.postValue(_error.value)

        name.value = text
    }
}