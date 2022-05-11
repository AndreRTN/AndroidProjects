package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.viewModels.QuizViewModel

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var tvImage: ImageView
    private lateinit var tvQuestion: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSubmit: Button
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var viewModel: QuizViewModel
    private var correctAswner: Int = 0
    private var mSelectedOptionPosition = -1
    private var mCurrentPosition = 0
    private lateinit var mSelectedOptionTv: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        tvImage = findViewById(R.id.iv_country)
        tvQuestion = findViewById(R.id.tv_question)
        tvProgress = findViewById(R.id.tv_progress)
        progressBar = findViewById(R.id.progress_bar)
        btnSubmit = findViewById(R.id.btn_submit)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)

        addOptionsClickListeners()
        viewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(QuizViewModel::class.java)

        viewModel.currentPosition.observe(this) {
            progressBar.progress = it
            tvProgress.text = "$it/${progressBar.max}"
            btnSubmit.text = "ENVIAR"
            mCurrentPosition = it
            if (it == 9) {
                btnSubmit.text = "FINALIZAR"
                btnSubmit.setOnClickListener(null)
            }
        }
        viewModel.question.observe(this) {
            tvQuestion.text = it.question
            tvOptionOne.text = it.optionOne
            tvOptionTwo.text = it.optionTwo
            tvOptionThree.text = it.optionThree
            tvOptionFour.text = it.optionFour
            tvImage.setImageResource(it.image)
            correctAswner = it.correctAnswer
        }

        viewModel.isCorrect.observe(this) {
            if (it != null) showAnswers()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> selectedOptionView(tvOptionOne, 0)
            R.id.tv_option_two -> selectedOptionView(tvOptionTwo, 1)
            R.id.tv_option_three -> selectedOptionView(tvOptionThree, 2)
            R.id.tv_option_four -> selectedOptionView(tvOptionFour, 3)
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == -1) customToast()
                else viewModel.submit(mSelectedOptionPosition)
            }
        }

    }

    private fun selectedOptionView(
        tv: TextView,
        selectedOptionNum: Int,
        color: Int = R.drawable.selected_option_border_bg
    ) {

        defaultColors(R.color.white)
        tv.setTextColor(Color.parseColor("#363A43"))
        mSelectedOptionPosition = selectedOptionNum
        mSelectedOptionTv = tv
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, color
        )
    }

    private fun defaultColors(color: Int) {
        val options = ArrayList<TextView>()

        tvOptionOne.let {
            options.add(0, it)
        }
        tvOptionTwo.let {
            options.add(1, it)
        }
        tvOptionThree.let {
            options.add(2, it)
        }
        tvOptionFour.let {
            options.add(3, it)
        }
        options.forEach {
            it.setTextColor(color)
            it.typeface = Typeface.DEFAULT
            it.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)

        }
    }

    private fun addOptionsClickListeners() {
        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

    }

    private fun showCorrectAnswer() {
        lateinit var tvCorrect: TextView;
        when (correctAswner) {
            1 -> tvCorrect = tvOptionOne
            2 -> tvCorrect = tvOptionTwo
            3 -> tvCorrect = tvOptionThree
            4 -> tvCorrect = tvOptionFour
        }
        tvCorrect.setTextColor(Color.parseColor("#363A43"))
        tvCorrect.setTypeface(tvCorrect.typeface, Typeface.BOLD)
        tvCorrect.background = ContextCompat.getDrawable(
            this, R.color.correct_option
        )

        if (correctAswner != mSelectedOptionPosition + 1) {
            mSelectedOptionTv.setTextColor(ContextCompat.getColor(this, R.color.white))
            mSelectedOptionTv.background = ContextCompat.getDrawable(this, R.color.incorrect_option)
        }
    }

    private fun showAnswers() {
        defaultColors(R.color.purple_500)
        showCorrectAnswer()
        tvOptionOne.setOnClickListener(null)
        tvOptionTwo.setOnClickListener(null)
        tvOptionThree.setOnClickListener(null)
        tvOptionFour.setOnClickListener(null)
        if (mCurrentPosition != 9) {
            btnSubmit.text = "PRÓXIMO"
            mSelectedOptionPosition = -1
            btnSubmit.setOnClickListener {
                viewModel.nextQuestion()
                addOptionsClickListeners()
                defaultColors(R.color.white)

            }
        }

        else {
            val intent = Intent(this, ResultQuizActivity::class.java)
            intent.putExtra("name", this.intent.getStringExtra("name"))
            intent.putExtra("pontuation", viewModel.points.value)
            startActivity(intent)

            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    fun customToast() {

        val layout = layoutInflater.inflate(
            R.layout.custom_toast_message,
            findViewById(R.id.custom_toast_container)
        )
        val txtToast: TextView = layout.findViewById(R.id.tv_custom_toast)

        Toast(this).apply {
            view = layout
            txtToast.text = "Escolha uma opção!"
            duration = Toast.LENGTH_SHORT

        }.show()
    }

}