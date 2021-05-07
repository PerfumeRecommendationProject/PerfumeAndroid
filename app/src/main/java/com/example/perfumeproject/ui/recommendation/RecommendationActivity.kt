package com.example.perfumeproject.ui.recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityHomeBinding
import com.example.perfumeproject.databinding.ActivityRecommendationBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationActivity : BaseActivity<ActivityRecommendationBinding>(R.layout.activity_recommendation) {
    override val viewModel: RecommendationViewModel by viewModels<RecommendationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

    }
}