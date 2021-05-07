package com.example.perfumeproject.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityRecommendationBinding
import com.example.perfumeproject.databinding.ActivitySearchBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override val viewModel: SearchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

    }
}