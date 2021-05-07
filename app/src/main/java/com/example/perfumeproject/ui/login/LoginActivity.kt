package com.example.perfumeproject.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.example.perfumeproject.R
import com.example.perfumeproject.databinding.ActivityLoginBinding
import com.example.perfumeproject.databinding.ActivitySplashBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.nexters.winepick.util.startActivity
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}