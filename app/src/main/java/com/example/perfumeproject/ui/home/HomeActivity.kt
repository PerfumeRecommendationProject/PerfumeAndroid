package com.example.perfumeproject.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityHomeBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.util.ConfirmDialog
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(com.example.perfumeproject.BR.vm, viewModel)


        viewModel.loginWarningDlg.observe(this, Observer {
            if (it) {
                ConfirmDialog(
                    title = "로그인 하시겠습니까?",
                    content = "스크랩 기능은\n로그인이 필요합니다.",
                    leftText = getString(R.string.test_warning_btn_left_text),
                    leftClickListener = {
                        it.dismiss()
                    },
                    rightText = getString(R.string.test_warning_btn_right_text),
                    rightClickListener = {
                        UserApiClient.instance.run {
                            if (isKakaoTalkLoginAvailable(this@HomeActivity)) {
                                loginWithKakaoTalk(this@HomeActivity, callback = baseCallback)
                            } else {
                                loginWithKakaoAccount(this@HomeActivity, callback = baseCallback)
                            }
                        }
                        it.dismiss()

                    },
                    cancelable = false
                ).show(supportFragmentManager, "LoginWarningDialog")
            }
        })

    }
}