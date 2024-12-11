package com.capstone.personaway.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.R
import com.capstone.personaway.databinding.ActivitySingleResultBinding
import com.capstone.personaway.ui.adapter.ResultItem

class SingleResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultItem = intent.getSerializableExtra("RESULT_ITEM") as? ResultItem
        resultItem?.let {
            binding.tvResult.text = getString(R.string.hasil_test_detail)
            binding.tvResultDetail.text = getString(R.string.hasil_analisis_detail)
            binding.tvResultDetailDate.text = it.date
            binding.tvResultDetailTime.text = it.time
            binding.tvResultCategory.text = it.result

        }
    }
}
