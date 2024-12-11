package com.capstone.personaway.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.personaway.api.ApiClient
import com.capstone.personaway.api.ResponseModel
import com.capstone.personaway.databinding.FragmentTestBinding
import com.capstone.personaway.model.ResultModel
import com.capstone.personaway.ui.SingleResultActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol Kirim Jawaban
        binding.btnKirim.setOnClickListener {
            val userInput = binding.etInput.text.toString()

            if (userInput.isNotBlank()) {
                sendTextToApi(userInput)
            } else {
                Toast.makeText(requireContext(), "Input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendTextToApi(inputText: String) {
        val jsonBody = """
            {
                "text": "$inputText"
            }
        """.trimIndent()

        val requestBody: RequestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())

        ApiClient.apiService.postText(requestBody).enqueue(object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("API Response", "Deskripsi: ${responseBody?.data?.deskripsi}")

                    toResult(responseBody)
                } else {
                    Log.e("API Error", "Code: ${response.code()}")
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengirimkan data: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun toResult(responseBody: ResultModel?) {
        val result = ResultModel(
            responseBody?.data!!,
            responseBody.image_url,
            responseBody.prediction,
            responseBody.status,
            "date",
            "time"
        )
        val intent = Intent(requireContext(), SingleResultActivity::class.java)
        intent.putExtra("RESULT_ITEM", result)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
