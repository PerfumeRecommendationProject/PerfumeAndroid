package com.example.perfumeproject.ui.recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.perfumeproject.BR
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.R
import com.example.perfumeproject.data.RecommendationConstant
import com.example.perfumeproject.databinding.ActivityHomeBinding
import com.example.perfumeproject.databinding.ActivityRecommendationBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeViewModel
import com.example.perfumeproject.ui.recommend_result.RecommendationResultActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.nexters.winepick.util.startActivity

@AndroidEntryPoint
class RecommendationActivity : BaseActivity<ActivityRecommendationBinding>(R.layout.activity_recommendation) {
    override val viewModel: RecommendationViewModel by viewModels<RecommendationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.startIntent.observe(this, Observer {
            if(it) {
                Intent(PerfumeApplication.appContext, RecommendationResultActivity::class.java).apply {
                    putExtra("mode", RecommendationConstant.NEW)
                    putExtra("desc", viewModel.searchDesc.value)
                }.run {
                    startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                }
            }
        })

    }
}