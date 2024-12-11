package com.capstone.personaway.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.personaway.R
import com.capstone.personaway.databinding.FragmentTestBinding
import com.capstone.personaway.api.ApiClient
import com.capstone.personaway.api.ResponseModel
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
        binding.llInput.findViewById<Button>(R.id.btnKirim).setOnClickListener {
            val userInput = binding.llInput.findViewById<EditText>(R.id.etInput).text.toString()

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

        ApiClient.apiService.postText(requestBody).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("API Response", "Deskripsi: ${responseBody?.data?.deskripsi}")

                    // Tampilkan hasil di Toast atau Update UI
                    Toast.makeText(
                        requireContext(),
                        "Deskripsi: ${responseBody?.data?.deskripsi}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Log.e("API Error", "Code: ${response.code()}")
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengirimkan data: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
