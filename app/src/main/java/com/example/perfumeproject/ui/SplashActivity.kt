package com.example.perfumeproject.ui

import android.os.Bundle
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivitySplashBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.login.LoginActivity
import com.example.perfumeproject.ui.login.getKakaoHashKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.nexters.winepick.util.startActivity
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity(
    override val viewModel: BaseViewModel? = null
) : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiScope.launch {
            delay(DURATION)
            Timber.e("${getKakaoHashKey(PerfumeApplication.appContext!!)}")
            startActivity(LoginActivity::class, isFinish = true)

        }
    }



    companion object {
        private const val DURATION: Long = 1500
    }
}