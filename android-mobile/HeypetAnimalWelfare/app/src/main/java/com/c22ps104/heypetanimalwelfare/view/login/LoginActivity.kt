package com.c22ps104.heypetanimalwelfare.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps104.heypetanimalwelfare.databinding.ActivityLoginBinding
import com.c22ps104.heypetanimalwelfare.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }
    }
}