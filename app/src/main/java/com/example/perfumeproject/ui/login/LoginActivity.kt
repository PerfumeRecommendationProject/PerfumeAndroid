package com.example.perfumeproject.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityLoginBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeActivity
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kr.co.nexters.winepick.util.startActivity


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.apply {
            btnLogin.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = callback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }
            tvGuest.setOnClickListener {
                authManager.token = "guest"
                authManager.serverToken = "guest"
                startActivity(HomeActivity::class, true)
            }
        }
    }

}
