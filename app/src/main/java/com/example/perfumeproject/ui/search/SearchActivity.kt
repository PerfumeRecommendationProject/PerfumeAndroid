package com.example.perfumeproject.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.perfumeproject.BR
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.R
import com.example.perfumeproject.data.RecommendationConstant
import com.example.perfumeproject.databinding.ActivitySearchBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeViewModel
import com.example.perfumeproject.ui.recommend_result.RecommendationResultActivity
import com.example.perfumeproject.util.ConfirmDialog
import com.example.perfumeproject.util.VerticalItemDecorator
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override val viewModel: SearchViewModel by viewModels<SearchViewModel>()
    private val perfumeAdapter: PerfumeDataAdapter by lazy { PerfumeDataAdapter(viewModel,authManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.rvPerfumeList.apply {
            adapter = perfumeAdapter
            this.addItemDecoration(VerticalItemDecorator(20))
        }


        viewModel.startIntent.observe(this, Observer {

            if(it) {
                Intent(PerfumeApplication.appContext, RecommendationResultActivity::class.java).apply {
                    putExtra("mode", RecommendationConstant.BASE)
                    putExtra("pId", viewModel.pID.value!!)
                }.run {
                    startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                }
            }
        })



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
                        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SearchActivity)) {
                            UserApiClient.instance.loginWithKakaoTalk(this@SearchActivity, callback = callback)
                        } else {
                            UserApiClient.instance.loginWithKakaoAccount(this@SearchActivity, callback = callback)
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