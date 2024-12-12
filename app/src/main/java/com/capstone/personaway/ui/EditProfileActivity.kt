package com.capstone.personaway.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.databinding.ActivityEditProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.io.InputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri: Uri = data?.data!!

                // Tampilkan gambar di ImageView
                binding.ivProfile.setImageURI(fileUri)

                // Konversi gambar ke Base64 dan simpan ke Firestore
                convertAndSaveImageToFirestore(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
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

        binding.etName.setText(auth.currentUser?.displayName)
        binding.tvEmail.text = auth.currentUser?.email

        binding.btnSave.setOnClickListener {
            performSaveAction()
        }

//        binding.ibEditProfile.setOnClickListener {
//            openImagePicker()
//        }

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnChangePassword.setOnClickListener() {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun performSaveAction() {
        val displayName = binding.etName.text.toString()
        if (displayName.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val profileUpdate = userProfileChangeRequest {
            this.displayName = displayName
        }

        auth.currentUser?.updateProfile(profileUpdate)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserToFirestore(displayName)
                } else {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirestore(displayName: String) {
        val userId = auth.currentUser?.uid
        val email = auth.currentUser?.email

        if (userId != null && email != null) {
            val firestore = Firebase.firestore
            val userDocument = firestore.collection("users").document(userId)

            val userData = mapOf(
                "uid" to userId,
                "email" to email,
                "displayName" to displayName,
                "profileImageBase64" to null // Default value
            )

            userDocument.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "User saved to Firestore", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertAndSaveImageToFirestore(imageUri: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Konversi ke Base64
            val base64String = bitmapToBase64(bitmap)

            // Simpan Base64 ke Firestore
            saveBase64ToFirestore(base64String)
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to process image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun saveBase64ToFirestore(base64String: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val firestore = Firebase.firestore
            val userDocument = firestore.collection("users").document(userId)

            userDocument.update("profileImageBase64", base64String)
                .addOnSuccessListener {
                    Toast.makeText(this, "Image saved to Firestore", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save image: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
