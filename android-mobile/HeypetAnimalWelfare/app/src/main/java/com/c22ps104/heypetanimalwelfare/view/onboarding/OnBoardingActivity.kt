package com.c22ps104.heypetanimalwelfare.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.c22ps104.heypetanimalwelfare.databinding.ActivityOnBoardingBinding
import com.c22ps104.heypetanimalwelfare.utils.bitmapToFile
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.main.MainActivity
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity.Companion.EXTRA_TOKEN
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity.Companion.EXTRA_USERNAME
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        val token = intent.getStringExtra(EXTRA_TOKEN)
        val name = intent.getStringExtra(EXTRA_USERNAME)

        binding.tvOnBoardingUsername.text = name

        binding.tvSkip.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }

        binding.btnFinish.setOnClickListener {
            // TODO("Insert photo for API request")
            val bio = binding.etBio.text.toString().toRequestBody("text/plain".toMediaType())
            val pet = binding.etBuddy.text.toString().toRequestBody("text/plain".toMediaType())
            val photo = bitmapToFile(binding.ivPhotoProfile.drawable.toBitmap(), this)

            if (token != null && name != null) {
                onBoardingViewModel.updateProfile(token, pet, bio, photo).observe(this) {
                    val intentToLogin = Intent(this, LoginActivity::class.java)
                    intentToLogin.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentToLogin)
                    finish()
                    Toast.makeText(application, "Profile Setup $it", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}