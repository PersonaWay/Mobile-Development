package com.capstone.personaway.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()

    }
    private fun setupAction() {
        binding.ibBack.setOnClickListener {
            finish()
        }
    }
}