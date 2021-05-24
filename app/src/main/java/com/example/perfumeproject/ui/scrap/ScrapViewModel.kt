package com.example.perfumeproject.ui.scrap

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.data.request.ScrapRequest
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.detail.PerfumeDetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : ScrapPerfumeViewModel(perfumeRepository) {

    private var _scrapData: MutableLiveData<Int> = MutableLiveData()
    var scrapData: LiveData<Int> = _scrapData

    init {
        _scrapData.value = 0
    }

    fun getScrapPerfume() {
        perfumeRepository.getScrapData(
            onSuccess = {
                if(it.isNullOrEmpty()) {
                    _scrapData.value = 0

                } else {
                    _perfumeData.value = it!!
                    _scrapData.value = it!!.size
                }

            }, onFailure = {

            }
        )
    }

    override fun perfumeItemClick(perfumeData: PerfumeData) {

        Intent(PerfumeApplication.appContext, PerfumeDetailActivity::class.java).apply {
            putExtra("perfumeData", perfumeData)
        }.run {
            PerfumeApplication.getGlobalApplicationContext()
                .startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }


    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
        getScrapPerfume()
    }
}

