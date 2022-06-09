package com.c22ps104.heypetanimalwelfare.view.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.databinding.ActivityOnBoradingBinding
import com.c22ps104.heypetanimalwelfare.view.bottomnavigation.BottomNavigationActivity
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnBoradingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoradingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvSkip.setOnClickListener {
            val intentToMain = Intent(this, LoginActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }

        binding.btnFinish.setOnClickListener {

            //TODO API: PUT data
            val intentToMain = Intent(this, LoginActivity::class.java)
            intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToMain)
            finish()
        }
    }
}