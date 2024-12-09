package com.capstone.personaway.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.personaway.databinding.FragmentHomeBinding
import com.capstone.personaway.ui.BehindTest1
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
            val intent = Intent(requireContext(), BehindTest1::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
