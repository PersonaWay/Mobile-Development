package com.capstone.personaway.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.personaway.R
import com.capstone.personaway.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottombar()
        setupAction()
    }

    private fun setupAction() {
        auth = Firebase.auth
        binding.welcomeUser.text = "Welcome, ${auth.currentUser!!.displayName}"
    }

    private fun bottombar() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Pindah ke HomeActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_test -> {
                    startActivity(Intent(this, TestActivity::class.java))
                    true
                }
                R.id.nav_result -> {
                    // Pindah ke NotificationsActivity
                    startActivity(Intent(this, ResultActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Pindah ke ProfileActivity
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}