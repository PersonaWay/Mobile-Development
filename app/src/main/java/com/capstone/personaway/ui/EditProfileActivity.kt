package com.capstone.personaway.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityEditProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    // Activity result launcher untuk ImagePicker
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                // Ambil URI gambar dari hasil
                val fileUri: Uri = data?.data!!

                // Tampilkan gambar di ImageView
                binding.ivProfile.setImageURI(fileUri)

                // Tambahkan logika upload gambar jika diperlukan
                uploadProfileImage(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                // Tampilkan pesan error
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                // Jika proses dibatalkan
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
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

        // Set data awal
        binding.etName.setText(auth.currentUser?.displayName)
        binding.tvEmail.text = auth.currentUser?.email

        // Simpan perubahan profil
        binding.btnSave.setOnClickListener {
            performSaveAction()
        }

        // Pilih gambar dari kamera atau galeri
        binding.ibEditProfile.setOnClickListener {
            openImagePicker()
        }

        // Tombol kembali
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop() // Opsi untuk memotong gambar
            .compress(1024) // Ukuran gambar di bawah 1MB
            .maxResultSize(1080, 1080) // Resolusi maksimum
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
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
