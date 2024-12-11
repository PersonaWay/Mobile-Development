package com.capstone.personaway.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.personaway.R
import com.capstone.personaway.databinding.SingleResultHistoryBinding
import java.io.Serializable

class ResultAdapter(
    private val resultList: List<ResultItem>,
    private val onItemClick: (ResultItem) -> Unit
) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(private val binding: SingleResultHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultItem) {
            binding.tvNumber.text = item.number.toString()
            binding.tvDate.text = item.date
            binding.tvTime.text = item.time
            binding.tvResult.text = item.result
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = SingleResultHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(resultList[position])
    }

    override fun getItemCount(): Int = resultList.size
}

data class ResultItem(
    val number: Int,
    val date: String,
    val time: String,
    val result: String
) : Serializable
