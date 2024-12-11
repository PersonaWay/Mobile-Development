package com.capstone.personaway.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityEditProfileBinding
import com.github.drjacky.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    // Launcher untuk menangani hasil ImagePicker
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri: Uri? = result.data?.data
            if (uri != null) {
                binding.ivProfile.setImageURI(uri) // Set gambar ke ImageView
                uploadProfileImage(uri) // Upload ke server jika diperlukan
            } else {
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction() {
        auth = Firebase.auth

        binding.etName.setText(auth.currentUser?.displayName)
        binding.tvEmail.text = auth.currentUser?.email

        binding.btnSave.setOnClickListener {
            performSaveAction()
        }

        binding.btnChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.ibEditProfile.setOnClickListener {
            openImagePicker()
        }

        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    private fun openImagePicker() {
        pickImageLauncher.launch(
            ImagePicker.with(this)
                .crop() // Memotong gambar jika diperlukan
                .galleryOnly() // Pilih dari galeri
                .createIntent()
        )
    }

    private fun performSaveAction() {
        val profileUpdate = userProfileChangeRequest {
            displayName = binding.etName.text.toString()
        }

        if (!profileUpdate.displayName.isNullOrEmpty() && profileUpdate.displayName != auth.currentUser?.displayName) {
            auth.currentUser?.updateProfile(profileUpdate)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        } else {
            Toast.makeText(this, "Name is empty or same as before", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadProfileImage(imageUri: Uri) {
        // Implementasikan logika upload gambar ke Firebase Storage atau server Anda
        Toast.makeText(this, "Image URI: $imageUri", Toast.LENGTH_SHORT).show()
    }
}
