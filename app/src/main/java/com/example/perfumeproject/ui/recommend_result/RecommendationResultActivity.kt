package com.example.perfumeproject.ui.recommend_result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.perfumeproject.BR
import com.example.perfumeproject.R
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.RecommendationConstant
import com.example.perfumeproject.databinding.ActivityRecommendationResultBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.recommendation.RecommendationViewModel
import com.example.perfumeproject.ui.search.PerfumeDataAdapter
import com.example.perfumeproject.util.ConfirmDialog
import com.example.perfumeproject.util.VerticalItemDecorator
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationResultActivity : BaseActivity<ActivityRecommendationResultBinding>(R.layout.activity_recommendation_result) {
    override val viewModel: RecommendationResultViewModel by viewModels<RecommendationResultViewModel>()
    private val perfumeAdapter: PerfumeDataAdapter by lazy { PerfumeDataAdapter(viewModel,authManager) }

    private val mode: String?
        get() = intent.getSerializableExtra("mode") as? String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.rvPerfumeList.apply {
            adapter = perfumeAdapter
            this.addItemDecoration(VerticalItemDecorator(20))
        }

        viewModel.setPerfumeMode(mode!!)

        if (mode == RecommendationConstant.NEW) {
            val perfumeDesc: String? = intent.getSerializableExtra("desc") as? String
            perfumeDesc?.let { viewModel.getPerfumeData(it)} ?: finish()

        } else {
            val perfumeId : Int? = intent.getSerializableExtra("pId") as? Int
            perfumeId?.let { viewModel.getPerfumeMatchData(it) } ?:finish()
        }

        viewModel.loginWarningDlg.observe(this, Observer {
            if (it) {
                ConfirmDialog(
                    title = getString(R.string.login_warning_title),
                    content = getString(R.string.login_warning_like),
                    leftText = getString(R.string.login_warning_btn_left_text),
                    leftClickListener = {
                        it.dismiss()
                    },
                    rightText = getString(R.string.login_warning_btn_right_text),
                    rightClickListener = {
                        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
                        } else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                        }
                        it.dismiss()

                    },
                    cancelable = false
                ).show(supportFragmentManager, "LoginWarningDialog")
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}