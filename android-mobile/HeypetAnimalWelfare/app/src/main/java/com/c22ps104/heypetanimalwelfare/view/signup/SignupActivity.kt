package com.c22ps104.heypetanimalwelfare.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.c22ps104.heypetanimalwelfare.databinding.ActivitySignupBinding
import com.c22ps104.heypetanimalwelfare.view.onboarding.OnBoardingActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.btnSignup.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val name = binding.etUsername.text.toString()

            signupViewModel.register(
                email = email,
                password = pass,
                name = name,
                bio = "",
                phoneNumber = ""
            )

            Log.d("registerFormAct", "$name $email")
        }

        signupViewModel.register.observe(this) {
            if (it != null) {
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()

                val intentToOnBoarding = Intent(this@SignupActivity, OnBoardingActivity::class.java)
                intentToOnBoarding.putExtra(EXTRA_TOKEN, it.data.token.accessToken)
                intentToOnBoarding.putExtra(EXTRA_USERNAME, it.data.user.name)
                startActivity(intentToOnBoarding)
                finish()
                RESULT_OK
            }
        }
    }

    companion object {
        const val EXTRA_TOKEN = "EXTRA_TOKEN"
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
    }
}