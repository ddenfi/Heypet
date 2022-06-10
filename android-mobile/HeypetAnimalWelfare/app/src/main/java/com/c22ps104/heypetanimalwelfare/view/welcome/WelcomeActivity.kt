package com.c22ps104.heypetanimalwelfare.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_IS_LOGIN
import com.c22ps104.heypetanimalwelfare.databinding.ActivityWelcomeBinding
import com.c22ps104.heypetanimalwelfare.view.main.BottomNavigationActivity
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesHelper = PreferencesHelper(this)

        setupView()
        checkLoginState()
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

    private fun checkLoginState() {
        if(preferencesHelper.getBoolean(PREF_IS_LOGIN)) {
            val intentToMain = Intent(this@WelcomeActivity, BottomNavigationActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }
    }
}