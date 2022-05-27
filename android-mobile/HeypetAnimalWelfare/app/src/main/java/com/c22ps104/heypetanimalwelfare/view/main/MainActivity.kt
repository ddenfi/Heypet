package com.c22ps104.heypetanimalwelfare.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.c22ps104.heypetanimalwelfare.databinding.ActivityMainBinding
import com.c22ps104.heypetanimalwelfare.view.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()

        binding.progressBar.visibility = View.VISIBLE
        binding.btnChat.setOnClickListener {
            val intentToSettings = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intentToSettings)
        }
    }
}