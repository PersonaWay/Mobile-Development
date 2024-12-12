package com.capstone.personaway.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.capstone.personaway.databinding.FragmentProfileBinding
import com.capstone.personaway.ui.EditProfileActivity
import com.capstone.personaway.ui.LoginActivity
import com.capstone.personaway.ui.onboarding.WelcomeActivity
import com.capstone.personaway.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        binding.tvName.text = auth.currentUser?.displayName ?: "User"
    }

    private fun setupAction() {
        auth = Firebase.auth
        sessionManager = SessionManager(requireContext())

        // Set user name
        binding.tvName.text = auth.currentUser?.displayName ?: "User"

        // Navigate to EditProfileActivity
        binding.btnEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnLanguage.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        // Logout action with confirmation dialog
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Keluar")
            setMessage("Apakah anda ingin keluar aplikasi?")
            setPositiveButton("Ya") { dialog, _ ->
                performLogout()
                dialog.dismiss()
            }
            setNegativeButton("Nanti dulu") { dialog, _ ->
                dialog.dismiss()
            }
            create().show()
        }
    }

    private fun performLogout() {
        auth.signOut() // Sign out from Firebase
        sessionManager.clearSession() // Clear saved session

        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
