package com.example.perfumeproject.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perfumeproject.R
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.databinding.RvScrapBinding
import kr.co.nexters.winepick.util.setOnSingleClickListener

class ScrapAdapter(val vm: ScrapPerfumeViewModel) :
    ListAdapter<PerfumeData, RecyclerView.ViewHolder>(PerfumeScrapDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RvScrapBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.rv_scrap, parent, false
        )
        return ScrapViewHolder(binding).apply {
            binding.root.setOnSingleClickListener {
                vm.perfumeItemClick(binding.perfumeData!!)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ScrapViewHolder).bind(getItem(position),vm)
    }
}

class ScrapViewHolder(private val binding: RvScrapBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(perfumeData: PerfumeData, vm: ScrapPerfumeViewModel) {
        binding.perfumeData = perfumeData
        binding.vm = vm
    }
}

object PerfumeScrapDiffUtilCallback : DiffUtil.ItemCallback<PerfumeData>() {
    override fun areItemsTheSame(
            oldItem: PerfumeData,
            newItem: PerfumeData
    ): Boolean {
        return oldItem.p_name == newItem.p_name
    }

    override fun areContentsTheSame(
            oldItem: PerfumeData,
            newItem: PerfumeData
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}

