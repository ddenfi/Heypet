package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_TOKEN
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_USER_NAME
import com.c22ps104.heypetanimalwelfare.databinding.ActivityEditProfileBinding
import com.c22ps104.heypetanimalwelfare.utils.bitmapToFile
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.onboarding.OnBoardingViewModel
import com.c22ps104.heypetanimalwelfare.view.signup.SignupActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditProfileBinding
    private val viewModel:OnBoardingViewModel by viewModels()
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesHelper = PreferencesHelper(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_save) {
            saveProfile()
            finish()
            Toast.makeText(applicationContext, "Reminder Saved", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveProfile() {
        val token = preferencesHelper.getString(PREF_TOKEN)
        val name = preferencesHelper.getString(PREF_USER_NAME)?.toRequestBody("text/plain".toMediaType())
        val bio = binding.etBio.text.toString().toRequestBody("text/plain".toMediaType())
        val pet = binding.etBuddy.text.toString().toRequestBody("text/plain".toMediaType())
        val photo = bitmapToFile(binding.ivPhotoProfile.drawable.toBitmap(), this)

        if (token != null && name != null) {
            viewModel.updateProfile(token, bio, pet, photo).observe(this) {
                if (it == "Success") {
                    finish()
                    Toast.makeText(application, "Update Profile $it", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(application, "Update Profile $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}