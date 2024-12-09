package com.capstone.personaway.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.personaway.R
import com.capstone.personaway.databinding.FragmentBehindTest2Binding

class BehindTest2Fragment : Fragment() {

    private var _binding: FragmentBehindTest2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBehindTest2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.kliklanjut.setOnClickListener {
            totest()
        }
    }

    private fun totest() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val testFragment = TestFragment()

        // Menambahkan fragment baru, atau menggantinya jika sudah ada
        fragmentTransaction.replace(R.id.fragment_container, testFragment)
        fragmentTransaction.addToBackStack(null) // Jika ingin memungkinkan kembali ke Fragment sebelumnya
        fragmentTransaction.commit()
    }

}