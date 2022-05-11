package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.viewModels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var etName: EditText

    private lateinit var model: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btn_start)
        etName = findViewById(R.id.et_name)

        model = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        ).get(MainActivityViewModel::class.java)

        btnStart.setOnClickListener {
            model.verifyName(etName.text.toString())
        }

        model.error.observe(this) {

            when (it) {
                true -> customToast()
                false -> {
                    val intent = Intent(this, QuizQuestionsActivity::class.java)
                    intent.putExtra("name", model.name.value)
                    startActivity(intent)
                    finish()
                }

            }

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
            txtToast.text = "Nome não pode ser vazio"
            duration = Toast.LENGTH_SHORT


        }.show()


    }
}