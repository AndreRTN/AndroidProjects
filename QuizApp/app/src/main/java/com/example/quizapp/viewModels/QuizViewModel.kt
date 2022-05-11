package com.example.quizapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.models.Question
import eu.tutorials.quizapp.Constants

class QuizViewModel : ViewModel() {

    private val _points: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 0
    }

    val points: MutableLiveData<Int> = _points
    private val _isCorrect: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val isCorrect: MutableLiveData<Boolean> = _isCorrect

    private val _currentPosition: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 0
    }
    val currentPosition: MutableLiveData<Int> = _currentPosition

    private val questions = Constants.getQuestions()
    private val _question: MutableLiveData<Question> = MutableLiveData<Question>().apply {
        value = questions[_currentPosition.value!!]
    }

    val question: MutableLiveData<Question> = _question


    fun submit(index: Int) {
        val isAnswerCorrect: Boolean = _question.value?.correctAnswer == index + 1
        if (isAnswerCorrect) {
            _points.value = _points.value?.plus(1)
            _points.postValue(_points.value)
            _isCorrect.value = true
        } else {
            _isCorrect.value = false
        }


    }

    fun nextQuestion() {
        if (_currentPosition.value?.plus(1) == questions.size) return
        _currentPosition.value = _currentPosition.value?.plus(1)
        _question.value = questions[_currentPosition.value!!]

    }
}
