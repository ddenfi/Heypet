package com.c22ps104.heypetanimalwelfare.view.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import com.c22ps104.heypetanimalwelfare.databinding.ActivityOnBoardingBinding
import com.c22ps104.heypetanimalwelfare.utils.bitmapToFile
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.onBoarding.OnBoardingViewModel
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity.Companion.EXTRA_TOKEN
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity.Companion.EXTRA_USERNAME

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
        val name = intent.getStringExtra(EXTRA_USERNAME)
        val token = intent.getStringExtra(EXTRA_TOKEN)

        binding.tvSkip.setOnClickListener {
            val intentToMain = Intent(this, LoginActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }

        binding.btnFinish.setOnClickListener {
            val bio = binding.etBio.text.toString()
            val pet = binding.etBuddy.text.toString()
            val photo = bitmapToFile(binding.ivPhotoProfile.drawable.toBitmap(),this)

            if (token != null && name != null){
                viewModel.updateProfile(token,bio,pet,photo).observe(this){
                    val intentToLogin = Intent(this, LoginActivity::class.java)
                    intentToLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentToLogin)
                    finish()
                    Toast.makeText(application,"Update Profile $it",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}