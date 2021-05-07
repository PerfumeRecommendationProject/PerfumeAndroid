package com.example.perfumeproject.ui.scrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityScrapBinding
import com.example.perfumeproject.databinding.ActivitySearchBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScrapActivity : BaseActivity<ActivityScrapBinding>(R.layout.activity_scrap) {
    override val viewModel: ScrapViewModel by viewModels<ScrapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

    }
}