package com.capstone.personaway.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.capstone.personaway.R
import com.capstone.personaway.databinding.ActivityMainBinding
import com.capstone.personaway.ui.fragment.BehindTest1Fragment
import com.capstone.personaway.ui.fragment.HomeFragment
import com.capstone.personaway.ui.fragment.ProfileFragment
import com.capstone.personaway.ui.fragment.ResultFragment
import com.capstone.personaway.ui.fragment.TestFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bottomBar: BottomNavigationView

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

        bottomBar = findViewById(R.id.bottom_navigation)
        bottomBar.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_test -> {
                    replaceFragment(BehindTest1Fragment())
                    true
                }
                R.id.nav_result -> {
                    replaceFragment(ResultFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        return true
    }
}