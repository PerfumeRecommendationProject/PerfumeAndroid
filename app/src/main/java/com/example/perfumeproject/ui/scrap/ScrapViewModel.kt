package com.example.perfumeproject.ui.scrap

import android.content.Intent
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : BaseViewModel(perfumeRepository) {

    fun ScrapClick() {
        if (auth.token != "guest") {
            Intent(PerfumeApplication.appContext, ScrapActivity::class.java).apply {
            }.run {
                PerfumeApplication.getGlobalApplicationContext()
                    .startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        } else {

        }
    }




    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
    }
}

