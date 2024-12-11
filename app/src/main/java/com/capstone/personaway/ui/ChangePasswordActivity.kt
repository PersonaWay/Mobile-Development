package com.capstone.personaway.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()

    }
    private fun setupAction() {
        binding.ibBack.setOnClickListener {
            finish()
        }
    }
}