package com.capstone.personaway.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.personaway.R

class BehindTest1Fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tobehindtest2()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_behind_test1, container, false)
    }

    private fun tobehindtest2() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val behindTestFragment = BehindTest2Fragment()


        fragmentTransaction.replace(R.id.fragment_container, behindTestFragment)
        fragmentTransaction.addToBackStack(null) // Jika ingin memungkinkan kembali ke Fragment sebelumnya
        fragmentTransaction.commit()
    }
}