package com.example.perfumeproject.data


import android.content.Intent
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.network.PerfumeService
import com.example.perfumeproject.ui.home.HomeActivity
import kr.co.nexters.winepick.util.safeEnqueue
import timber.log.Timber
import javax.inject.Inject

class PerfumeRepository @Inject constructor(
    private val api: PerfumeService, private val authManager: AuthManager
) {
    val dictionaries: MutableMap<Int, Array<String>> = mutableMapOf()

    init {
        val appContext = PerfumeApplication.getGlobalApplicationContext()
    }


//    fun getWine(
//        wineId: Int,
//        onSuccess: (WineResult) -> Unit,
//        onFailure: () -> Unit
//    ) {
//        api.getWine(wineId).safeEnqueue(
//            onSuccess = { onSuccess(it.result!!) },
//            onFailure = { onFailure() },
//            onError = { onFailure() }
//        )
//    }


//
//    fun updateUser(
//        token: String,
//        personalityType: String?,
//        userId: Long,
//        successAction: (() -> Unit)? = null,
//        failAction: (() -> Unit)? = null
//    ) {
//        postUser(
//            data = AccessTokenData(token, personalityType!!, userId),
//            onSuccess = {
//                authManager.token = it.accessToken.toString()
//                authManager.autoLogin = true
//                authManager.id = it.id!!
//                authManager.testType = it.personalityType!!
//                successAction?.let { it() }
//
//                Intent(PerfumeApplication.appContext, HomeActivity::class.java).apply {
//                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run {
//                    PerfumeApplication.getGlobalApplicationContext().startActivity(this)
//                }
//            },
//            onFailure = {
//
//                failAction?.let { it() }
//
//                // _toastMeesageText.value = WinePickApplication.getGlobalApplicationContext()
//                //     .resources.getString(R.string.api_error)
//            }
//        )
//    }
}
