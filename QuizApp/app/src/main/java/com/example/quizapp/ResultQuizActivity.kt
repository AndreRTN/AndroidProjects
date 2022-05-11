package com.example.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.viewModels.MainActivityViewModel
import com.example.quizapp.viewModels.QuizViewModel

class ResultQuizActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvPontuation: TextView
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var quizViewModel: QuizViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_quiz)
        tvName = findViewById(R.id.tv_name)
        tvPontuation = findViewById(R.id.tv_pontuation)

        mainViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        ).get(MainActivityViewModel::class.java)
        quizViewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(QuizViewModel::class.java)

        tvName.text = intent.getStringExtra("name")
        tvPontuation.text = "Sua pontução foi ${intent.getIntExtra("pontuation", 0)} de 9"



    }
}