package com.example.motivation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.motivation.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener(this)
        verifyUserName()
    }

    override fun onClick(p0: View) {
        if (p0.id == R.id.button_save) {
            handleSave()
        }

    }

    private fun verifyUserName() {
        val name = SecurityPreferences(this).getString("USER_NAME")

        if(name.isNotEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleSave() {

        val name = binding.editName.text.toString()

        if (name.isNotEmpty()) {
            SecurityPreferences(this).storeString("USER_NAME", name)
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } else {
            Toast.makeText(this, R.string.validation_mandatory_name, Toast.LENGTH_SHORT).show()

        }
    }

}