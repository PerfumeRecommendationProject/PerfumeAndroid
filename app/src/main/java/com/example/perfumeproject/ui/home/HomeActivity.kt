package com.example.perfumeproject.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityHomeBinding
import com.example.perfumeproject.databinding.ActivityLoginBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.util.ConfirmDialog
import com.kakao.sdk.auth.LoginClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)


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
                        LoginClient.instance.run {
                            if (isKakaoTalkLoginAvailable(this@HomeActivity)) {
                                loginWithKakaoTalk(this@HomeActivity, callback = callback)
                            } else {
                                loginWithKakaoAccount(this@HomeActivity, callback = callback)
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