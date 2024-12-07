package com.capstone.personaway.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.R
import com.capstone.personaway.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottombar()
    }

    private fun bottombar() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Pindah ke HomeActivity
                    if (javaClass != MainActivity::class.java) { // Periksa kelas saat ini
                        startActivity(Intent(this, MainActivity::class.java))
                        finish() // Tutup aktivitas saat ini
                    }
                    true
                }
                R.id.nav_test -> {
                    if (javaClass != TestActivity::class.java) { // Periksa kelas saat ini
                        startActivity(Intent(this, TestActivity::class.java))
                        finish() // Tutup aktivitas saat ini
                    }
                    true
                }
                R.id.nav_result -> {
                    // Pindah ke ResultActivity
                    if (javaClass != ResultActivity::class.java) { // Periksa kelas saat ini
                        startActivity(Intent(this, ResultActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_profile -> {
                    // Pindah ke ProfileActivity
                    if (javaClass != ProfileActivity::class.java) { // Periksa kelas saat ini
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}