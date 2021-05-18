package com.example.perfumeproject.ui.scrap

import android.os.Bundle
import androidx.activity.viewModels
import com.example.perfumeproject.BR
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityScrapBinding
import com.example.perfumeproject.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScrapActivity : BaseActivity<ActivityScrapBinding>(R.layout.activity_scrap) {
    override val viewModel: ScrapViewModel by viewModels<ScrapViewModel>()
    private val scrapAdapter: ScrapAdapter by lazy { ScrapAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.rvScrap.apply {
            adapter = scrapAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}