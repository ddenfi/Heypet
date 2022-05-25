package com.c22ps104.heypetanimalwelfare.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps104.heypetanimalwelfare.databinding.ActivitySignupBinding
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.btnSignup.setOnClickListener {
            val intentToLogin = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intentToLogin)
            finish()
        }
    }
}