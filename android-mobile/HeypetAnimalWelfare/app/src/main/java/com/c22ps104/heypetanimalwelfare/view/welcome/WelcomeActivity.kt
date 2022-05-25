package com.c22ps104.heypetanimalwelfare.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps104.heypetanimalwelfare.databinding.ActivityWelcomeBinding
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val intentToLogin = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intentToLogin)
        }

        binding.btnSignup.setOnClickListener {
            val intentToSignup = Intent(this@WelcomeActivity, SignupActivity::class.java)
            startActivity(intentToSignup)
        }
    }
}