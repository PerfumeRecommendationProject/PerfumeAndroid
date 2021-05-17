package com.example.perfumeproject.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityLoginBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kr.co.nexters.winepick.util.startActivity
import org.koin.core.qualifier._q
import timber.log.Timber


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    val kakaoCallback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e("로그인 실패- $error")
        } else if (token != null) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Timber.e("토큰 정보 보기 실패 $error")
                } else if (tokenInfo != null) {
                    Timber.e("token - ${token.accessToken}, id - ${tokenInfo.id}")
                    authManager.token = token.accessToken
                    authManager.id = tokenInfo.id
                    Intent(PerfumeApplication.appContext, HomeActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run {
                        startActivity(this)
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.apply {
            btnLogin.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = kakaoCallback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = kakaoCallback)
                }
            }
            tvGuest.setOnClickListener {
                authManager.token = "guest"
                startActivity(HomeActivity::class, true)
            }
        }
    }

}
