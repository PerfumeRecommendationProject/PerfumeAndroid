package com.example.perfumeproject.ui

import android.os.Bundle
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivitySplashBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.perfumeproject.ui.login.LoginActivity
import com.kakao.sdk.user.UserApiClient
import kr.co.nexters.winepick.util.startActivity
import java.util.*


@AndroidEntryPoint
class SplashActivity(
    override val viewModel: BaseViewModel? = null
) : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiScope.launch {
            delay(DURATION)
            authManager.autoLogin = true
            authManager.token = "1111"
            checkToken()



        }
    }

    private fun checkToken() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                authManager.autoLogin = false
                startActivity(LoginActivity::class, isFinish = true)
            } else if (tokenInfo != null) {
                UserApiClient.instance.me { user, error ->
                    val currentDate: Date = Date()
                    val diffDay =
                        (currentDate.time - user!!.connectedAt!!.time) / (24 * 60 * 60 * 1000)
                    if (diffDay > 14) {
                        UserApiClient.instance.unlink {
                            authManager.autoLogin = false
                            startActivity(LoginActivity::class, isFinish = true)
                        }
                    } else {
                        if (authManager.autoLogin) {
                            startActivity(HomeActivity::class, isFinish = true)
                        } else {
                            startActivity(LoginActivity::class, isFinish = true)
                        }
                    }
                }
            } else {
                authManager.autoLogin = false
                startActivity(LoginActivity::class, isFinish = true)

            }
        }
    }



    companion object {
        private const val DURATION: Long = 1500
    }
}