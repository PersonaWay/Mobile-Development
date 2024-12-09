package com.capstone.personaway.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.personaway.R

class BehindTest2Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        totest()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_behind_test2, container, false)
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