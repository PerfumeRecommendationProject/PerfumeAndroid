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
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kr.co.nexters.winepick.util.startActivity
import timber.log.Timber


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()

     val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Timber.e("콜백??!ㅣㅏㅓ이마ㅓㄹ;")
        Timber.e("error - ${error}, token- ${token}")
        if (error != null) {
            Timber.e("로그인 실패 ${error}")
        } else if (token != null) {
            //Login Success
            Timber.d("로그인 성공")
            authManager.apply {
                this.token = token.accessToken
            }
            UserApiClient.instance.me { user, error ->
                val kakaoId = user!!.id

                Intent(PerfumeApplication.appContext, HomeActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run {
                    startActivity(this)
                }

            }
            Timber.d("로그인성공 - 토큰 ${authManager.token}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.apply {
            btnLogin.setOnClickListener {
                Timber.e("dd?")
                LoginClient.instance.run {
                    if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                        loginWithKakaoTalk(this@LoginActivity, callback = callback)
                        Timber.e("ddd?")
                    } else {
                        Timber.e("no kakao")
                        loginWithKakaoAccount(this@LoginActivity, callback = callback)
                    }
                }
            }
            tvGuest.setOnClickListener {
                authManager.token = "guest"
                startActivity(HomeActivity::class, true)
            }
        }
    }

    /** 카카오 로그인 서버 통신 */
    private fun addUserInfo(token: String,userId: Long) {
        Intent(PerfumeApplication.appContext, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.run {
            startActivity(this)
        }
    }
}
