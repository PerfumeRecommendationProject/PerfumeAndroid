package com.example.perfumeproject.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perfumeproject.R
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.databinding.ItemPerfumeDescriptionBinding
import com.example.perfumeproject.databinding.RvScrapBinding
import kr.co.nexters.winepick.util.setOnSingleClickListener

class NoteAdapter(val vm: PerfumeDetailViewModel) :
    ListAdapter<String, NoteViewHolder>(PerfumeNoteDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPerfumeDescriptionBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.item_perfume_description, parent, false
        )
        return NoteViewHolder(binding)
        }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NoteViewHolder(private val binding: ItemPerfumeDescriptionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(string: String) {
        binding.note = string
    }
}

object PerfumeNoteDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
            oldItem: String,
            newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
            oldItem: String,
            newItem: String
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}

