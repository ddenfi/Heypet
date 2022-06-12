package com.c22ps104.heypetanimalwelfare.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_ID
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_IS_LOGIN
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_TOKEN
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_USER_NAME
import com.c22ps104.heypetanimalwelfare.databinding.ActivityLoginBinding
import com.c22ps104.heypetanimalwelfare.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesHelper = PreferencesHelper(this)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            Log.d("LoginFrom","$email $password")
            loginViewModel.login(email, password)

            loginViewModel.login.observe(this) {
                if (it != null) {
                    saveSession(it.data.token.accessToken, it.data.user.id.toString(), it.data.user.name)

                    val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                    intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentToMain)
                    finish()
                }
            }
        }
    }

    private fun saveSession(accessToken: String, userId: String,userName:String) {
        preferencesHelper.putString(PREF_TOKEN, accessToken)
        preferencesHelper.putString(PREF_ID, userId)
        preferencesHelper.putBoolean(PREF_IS_LOGIN, true)
        preferencesHelper.putString(PREF_USER_NAME,userName)
    }
}