package com.capstone.personaway.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityEditProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()

    }
    private fun setupAction() {
        auth = Firebase.auth

        binding.etName.setText(auth.currentUser!!.displayName)
        binding.tvEmail.text = auth.currentUser!!.email

        binding.btnSave.setOnClickListener {
            performSaveAction()
        }

        binding.btnChangePassword.setOnClickListener {
//            showDiscardConfirmationDialog(false)
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.ibBack.setOnClickListener {
//            showDiscardConfirmationDialog()
            finish()
        }
    }

    private fun performSaveAction() {
        val profileUpdate = userProfileChangeRequest {
            displayName = binding.etName.text.toString()
        }

        if (!profileUpdate.displayName.isNullOrEmpty() && profileUpdate.displayName != auth.currentUser!!.displayName) {
            auth.currentUser!!.updateProfile(profileUpdate)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        } else {
            Toast.makeText(this, "Name is empty or same as before", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDiscardConfirmationDialog(back: Boolean = true) {
        AlertDialog.Builder(this)
            .setTitle("Discard changes")
            .setMessage("Are you sure you want to discard changes?")
            .setPositiveButton("Yes") { dialog, _ ->
                if (back) {
                    finish()
                } else {
                    val intent = Intent(this, ChangePasswordActivity::class.java)
                    startActivity(intent)
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}