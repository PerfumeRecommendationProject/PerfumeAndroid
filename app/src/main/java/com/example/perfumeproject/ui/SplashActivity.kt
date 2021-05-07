package com.example.perfumeproject.ui

import android.os.Bundle
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivitySplashBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.perfumeproject.ui.login.LoginActivity
import kr.co.nexters.winepick.util.startActivity


@AndroidEntryPoint
class SplashActivity(
    override val viewModel: BaseViewModel? = null
) : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiScope.launch {
            delay(DURATION)
            startActivity(LoginActivity::class, isFinish = true)

        }
    }



    companion object {
        private const val DURATION: Long = 1500
    }
}