package com.example.perfumeproject

import android.app.Application
import android.content.Context
import com.kakao.auth.*
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class PerfumeApplication : Application() {
    companion object {
        var appContext : Context? = null

        fun getGlobalApplicationContext(): Context {
            checkNotNull(appContext) { "This Application does not inherit com.kakao.GlobalApplication" }
            return appContext!!
        }

        fun getGlobalAppApplication() : PerfumeApplication {
            checkNotNull(appContext) { "This Application does not inherit com.kakao.GlobalApplication" }
            return appContext!! as PerfumeApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this,getString(R.string.kakao_app_key))
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
