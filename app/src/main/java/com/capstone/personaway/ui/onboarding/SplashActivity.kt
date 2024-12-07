package com.capstone.personaway.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            goToWelcomeActivity()
        }, 3000L)
    }

    private fun goToWelcomeActivity() {
        Intent(this, WelcomeActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}