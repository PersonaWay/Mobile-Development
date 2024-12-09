package com.capstone.personaway.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personaway.R
import com.capstone.personaway.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupBehindTestClickListener()
    }

    private fun setupAction() {
        auth = FirebaseAuth.getInstance()
        val userName = auth.currentUser?.displayName ?: "User"
        binding.welcomeUser.text = "Welcome, $userName"
    }

    private fun setupBehindTestClickListener() {
        // Set click listener for the behindtest view
        binding.behindtest.setOnClickListener {
            // Periksa apakah Fragment belum ada di FragmentManager
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            val behindTestFragment = BehindTest1Fragment()

            // Menambahkan fragment baru, atau menggantinya jika sudah ada
            fragmentTransaction.replace(R.id.fragment_container, behindTestFragment)
            fragmentTransaction.addToBackStack(null) // Jika ingin memungkinkan kembali ke Fragment sebelumnya
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
