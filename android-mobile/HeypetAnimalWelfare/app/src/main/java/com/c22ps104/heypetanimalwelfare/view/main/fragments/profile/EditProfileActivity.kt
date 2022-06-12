package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_TOKEN
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_USER_NAME
import com.c22ps104.heypetanimalwelfare.databinding.ActivityEditProfileBinding
import com.c22ps104.heypetanimalwelfare.utils.bitmapToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesHelper = PreferencesHelper(this)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        binding.tvEditProfileUsername.text = preferencesHelper.getString(PREF_USER_NAME)

        val token = preferencesHelper.getString(PREF_TOKEN)!!
        editProfileViewModel.getUserDetail(token)

        editProfileViewModel.userDetails.observe(this) {
            Glide.with(this)
                .load(it.photo)
                .into(binding.ivPhotoProfile)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveProfile() {
        // TODO("Insert photo for API request")
        val token = preferencesHelper.getString(PREF_TOKEN)

        val username =
            preferencesHelper.getString(PREF_USER_NAME)?.toRequestBody("text/plain".toMediaType())
        val bio = binding.etBio.text.toString().toRequestBody("text/plain".toMediaType())
        val pet = binding.etBuddy.text.toString().toRequestBody("text/plain".toMediaType())
        val photo = bitmapToFile(binding.ivPhotoProfile.drawable.toBitmap(), this)

        if (token != null && username != null) {
            editProfileViewModel.updateProfile(token, pet, bio, photo).observe(this) {
                if (it == "Success") finish()
                Toast.makeText(application, "Profile Edit $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}