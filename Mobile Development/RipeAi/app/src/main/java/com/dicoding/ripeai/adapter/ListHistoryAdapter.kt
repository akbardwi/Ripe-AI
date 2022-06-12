package com.dicoding.ripeai.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.core.util.*
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ripeai.R
import com.dicoding.ripeai.databinding.RowItemHistoryBinding
import com.dicoding.ripeai.datastore.response.DataItem
import com.dicoding.ripeai.datastore.response.HistoryResponse
import retrofit2.Call

class ListHistoryAdapter(private val listHistory: ArrayList<DataItem>) : RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: RowItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(items: ArrayList<DataItem>) {
        listHistory.clear()
        listHistory.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = RowItemHistoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = listHistory[position]

        holder.binding.apply {
            fruitName.text = history.fruit
            description.text = history.ripeness
            Glide.with(holder.binding.root)
                .load(history.imgUrl)
                .circleCrop()
                .into(imgFruit)
        }
    }
    override fun getItemCount(): Int = listHistory.size


}