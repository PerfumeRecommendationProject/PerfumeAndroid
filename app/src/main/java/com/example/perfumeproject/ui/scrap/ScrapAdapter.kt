package kr.co.nexters.winepick.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perfumeproject.R
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.databinding.RvScrapBinding
import com.example.perfumeproject.ui.scrap.ScrapViewModel

class WineFoodAdapter(val vm: ScrapViewModel) :
    ListAdapter<PerfumeData, RecyclerView.ViewHolder>(WineFoodDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RvScrapBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.rv_scrap, parent, false
        )
        return ScrapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ScrapViewHolder).bind(getItem(position))
    }
}

class ScrapViewHolder(private val binding: RvScrapBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(perfumeData: PerfumeData) {
        binding.perfumeData = perfumeData
    }
}

object WineFoodDiffUtilCallBack : DiffUtil.ItemCallback<PerfumeData>() {
    override fun areItemsTheSame(
        oldItem: PerfumeData,
        newItem: PerfumeData
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: PerfumeData,
        newItem: PerfumeData
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}

