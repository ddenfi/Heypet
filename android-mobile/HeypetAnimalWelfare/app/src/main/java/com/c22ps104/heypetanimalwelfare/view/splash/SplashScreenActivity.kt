package com.c22ps104.heypetanimalwelfare.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.view.welcome.WelcomeActivity

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashScreenDelay = 1333L

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed( {
            val intentToWelcome = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
            startActivity(intentToWelcome)
            finish()
        }, splashScreenDelay)
    }
}