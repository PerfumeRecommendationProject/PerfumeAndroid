package com.example.perfumeproject.ui.home

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.recommendation.RecommendationActivity
import com.example.perfumeproject.ui.scrap.ScrapActivity
import com.example.perfumeproject.ui.search.SearchActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : BaseViewModel(perfumeRepository) {

    private var _loginWarningDlg : MutableLiveData<Boolean> = MutableLiveData()
    val loginWarningDlg : LiveData<Boolean> = _loginWarningDlg

    /** 생성자 */
    init {
    }

    fun SearchClick() {
        Intent(PerfumeApplication.appContext, SearchActivity::class.java).apply {
        }.run {
            PerfumeApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }


    fun RecommendationClick() {
        Intent(PerfumeApplication.appContext, RecommendationActivity::class.java).apply {
        }.run {
            PerfumeApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun ScrapClick() {
        if (auth.token != "guest") {
            Intent(PerfumeApplication.appContext, ScrapActivity::class.java).apply {
            }.run {
                PerfumeApplication.getGlobalApplicationContext()
                    .startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        } else {
            _loginWarningDlg.value = true
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

