package com.example.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _character : MutableLiveData<Character> = MutableLiveData()

    val character = _character
    fun addCharacter(character: Character) {
        _character.value = character
        _character.postValue(_character.value)
    }
}