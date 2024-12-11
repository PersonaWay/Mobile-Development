package com.capstone.personaway.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.personaway.R
import com.capstone.personaway.databinding.ActivitySingleResultBinding
import com.capstone.personaway.model.ResultModel
import com.squareup.picasso.Picasso

class SingleResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        fetchData()
    }

    private fun fetchData() {
        val LABELS = mutableListOf("Dominance", "Influence", "Steadiness", "Compliance")

        val resultItem = intent.getSerializableExtra("RESULT_ITEM") as? ResultModel
        resultItem?.let {
            binding.tvResult.text = getString(R.string.hasil_test_detail)
            binding.tvResultDetail.text = getString(R.string.hasil_analisis_detail)
            binding.tvResultDetailDate.text = it.date
            binding.tvResultDetailTime.text = it.time
            binding.tvResultDescription.text = it.data.deskripsi
            binding.tvResultJob.text = it.data.pekerjaan
            binding.tvResultCorrelation.text = it.data.hubungan
            binding.tvResultCategory.text = LABELS[it.data.predik]

            Picasso.get()
                .load(it.image_url)
                .into(binding.ivResultCategory)
        }
    }
}
