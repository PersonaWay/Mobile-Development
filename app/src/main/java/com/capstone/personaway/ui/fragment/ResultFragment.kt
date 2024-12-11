package com.capstone.personaway.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.personaway.model.DataModel
import com.capstone.personaway.databinding.FragmentResultBinding
import com.capstone.personaway.ui.adapter.ResultAdapter
import com.capstone.personaway.model.ResultModel
import com.capstone.personaway.ui.SingleResultActivity

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyData = listOf(
            ResultModel(
                DataModel(
                    "deskripsi",
                    "hubungan",
                    "label",
                    "pekerjaan",
                    1
                ),
                "image_url",
                1,
                "status",
                "date",
                "time"
            ),
        )

        val adapter = ResultAdapter(dummyData) { item ->
            val intent = Intent(requireContext(), SingleResultActivity::class.java)
            intent.putExtra("RESULT_ITEM", item)
            startActivity(intent)
        }

        binding.rvResult.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResult.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
