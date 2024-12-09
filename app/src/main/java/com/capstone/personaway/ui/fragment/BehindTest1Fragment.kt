package com.capstone.personaway.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.personaway.R
import com.capstone.personaway.databinding.FragmentBehindTest1Binding

class BehindTest1Fragment : Fragment() {

    private var _binding: FragmentBehindTest1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentBehindTest1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.kliklanjut.setOnClickListener {
            tobehindtest2()
        }
    }

    private fun tobehindtest2() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val behindTestFragment = BehindTest2Fragment()

        fragmentTransaction.replace(R.id.fragment_container, behindTestFragment)
        fragmentTransaction.addToBackStack(null) // Jika ingin memungkinkan kembali ke Fragment sebelumnya
        fragmentTransaction.commit()
    }
}