package com.capstone.personaway.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityChangePasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()

    }
    private fun setupAction() {
        auth = Firebase.auth

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnChangePassword.setOnClickListener {
            performChangePassword()
        }
    }

    private fun performChangePassword() {
        val user = auth.currentUser

        val oldPassword = binding.etOldPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (oldPassword.isEmpty()) {
            binding.etOldPassword.error = "Old password is required"
            binding.etOldPassword.requestFocus()
            return
        }

        if (newPassword.isEmpty() || newPassword.length < 8) {
            binding.etNewPassword.error = "Password must be at least 8 characters"
            binding.etNewPassword.requestFocus()
            return
        }

        if (confirmPassword.isEmpty() || confirmPassword.length < 8) {
            binding.etConfirmPassword.error = "Password must be at least 8 characters"
            binding.etConfirmPassword.requestFocus()
            return
        }

        if (newPassword != confirmPassword) {
            binding.etConfirmPassword.error = "Password not match"
            binding.etConfirmPassword.requestFocus()
            return
        }

        val credential = EmailAuthProvider.getCredential(user!!.email!!, oldPassword)

        user.reauthenticate(credential).addOnCompleteListener() { reAuthTask ->
            if (reAuthTask.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Old Password is incorrect!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}