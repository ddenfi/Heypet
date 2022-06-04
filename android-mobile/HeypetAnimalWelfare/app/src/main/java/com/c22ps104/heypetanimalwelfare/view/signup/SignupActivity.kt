package com.c22ps104.heypetanimalwelfare.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import androidx.activity.viewModels
import com.c22ps104.heypetanimalwelfare.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel:SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.btnSignup.setOnClickListener {
            val _email = binding.etEmail.text.toString()
            val _pass = binding.etPassword.text.toString()
            val _name = binding.etUsername.text.toString()
            viewModel.register(email = _email, password = _pass, name = _name, bio = "", phoneNumber = "")
            Log.d("registerFormAct","$_name $_email")
        }

        viewModel.register.observe(this) {
            if (it != null ) {
                Toast.makeText(this,"Register Success",Toast.LENGTH_SHORT).show()
                val intentToLogin = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intentToLogin)
                finish()

                RESULT_OK
            }
        }

    }
}