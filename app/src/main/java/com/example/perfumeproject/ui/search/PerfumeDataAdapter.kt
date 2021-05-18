package com.example.perfumeproject.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perfumeproject.R
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.databinding.ItemPerfumeBinding
import com.example.perfumeproject.di.AuthManager
import kr.co.nexters.winepick.util.setOnSingleClickListener

/**
 * 검색 결과 아이템 recyclerview adapter
 *
 * @since v1.0.0 / 2021.02.08
 */
class PerfumeDataAdapter(val vm: PerfumeViewModel, val authManager: AuthManager) :
    ListAdapter<PerfumeData, PerfumeDataViewHolder>(PerfumeDataDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfumeDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPerfumeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_perfume, parent, false)

        return PerfumeDataViewHolder(binding).apply {
            binding.root.setOnSingleClickListener {
                vm.perfumeItemClick(binding.perfumeData!!)
                binding.perfumeData = binding.perfumeData!!.apply { isSelected = !this.isSelected!! }

            }

            binding.imgPerfumeLike.setOnSingleClickListener {
                // 미연의 클릭 방지를 위해 강제로 toggle 처리한다.
                if(authManager.token != "guest") {
                    binding.perfumeData = binding.perfumeData!!.apply { likeYn = !this.likeYn!! }
                }
                vm.perfumeLikeClick(binding.perfumeData!!)
            }
        }
    }

    override fun onBindViewHolder(holder: PerfumeDataViewHolder, position: Int) {
        holder.bind(getItem(position), vm)
    }
}

class PerfumeDataViewHolder(private val binding: ItemPerfumeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(perfumeData: PerfumeData, vm: PerfumeViewModel) {
        binding.perfumeData = perfumeData
        binding.vm = vm

    }
}

object PerfumeDataDiffUtilCallBack : DiffUtil.ItemCallback<PerfumeData>() {
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
