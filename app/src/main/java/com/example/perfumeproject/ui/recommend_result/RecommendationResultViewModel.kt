package com.example.perfumeproject.ui.recommend_result

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.data.RecommendationConstant
import com.example.perfumeproject.data.request.ScrapRequest
import com.example.perfumeproject.data.request.perfumeDescRequest
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.detail.PerfumeDetailActivity
import com.example.perfumeproject.ui.search.PerfumeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommendationResultViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : PerfumeViewModel(perfumeRepository) {

    private var _loginWarningDlg: MutableLiveData<Boolean> = MutableLiveData()
    val loginWarningDlg: LiveData<Boolean> = _loginWarningDlg

    private var _searchDesc: MutableLiveData<String> = MutableLiveData()
    var searchDesc: LiveData<String> = _searchDesc

    private var _perfumeNum : MutableLiveData<Int> = MutableLiveData()
    var perfumeNum : LiveData<Int> = _perfumeNum

    private var _mode: MutableLiveData<String> = MutableLiveData()
    var perfumeMode: LiveData<String> = _mode

    private var _pID: MutableLiveData<Int> = MutableLiveData()
    var pID: LiveData<Int> = _pID



    init {
        _perfumeNum.value = 0
        auth.search = false

    }

    fun setPerfumeMode(mode:String) {
        _mode.value = mode
    }

    fun getPerfumeData(desc : String) {
        showLoading()
        _searchDesc.value = desc
        perfumeRepository.getNewPerfumeList(perfumeDescRequest(desc),
            onSuccess = {
                if(!it.isNullOrEmpty()) {
                    _perfumeData.value = it
                    _perfumeNum.value = it.size
                }
                hideLoading()
            },
            onFailure = {
                hideLoading()
            })

    }

    fun getPerfumeMatchData(pId : Int) {
        showLoading()
        _pID.value = pId
        perfumeRepository.postBasedPerfume(scrapRequest = ScrapRequest(pId),
            onSuccess = {
                if(!it.isNullOrEmpty()) {
                    _perfumeData.value = it
                    _perfumeNum.value = it.size
                }
                hideLoading()
            },
            onFailure = {
                hideLoading()
            })
    }

    override fun perfumeItemClick(perfumeData: PerfumeData) {
        perfumeData.isSelected = false
        Intent(PerfumeApplication.appContext, PerfumeDetailActivity::class.java).apply {
            putExtra("perfumeData", perfumeData)
        }.run {
            PerfumeApplication.getGlobalApplicationContext()
                .startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun perfumeLikeClick(perfumeData: PerfumeData) {
        if (auth.token != "guest") {
            perfumeRepository.putScrap(
                scrapRequest = ScrapRequest(perfumeData.p_idx),
                onSuccess = {
                    Timber.d("향수 스크랩 성공")
                },
                onFailure = {
                    Timber.d("향수 스크랩 실패")
                }
            )
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
        if (_mode.value == RecommendationConstant.NEW) {
            getPerfumeData(_searchDesc.value!!)

        } else {
            getPerfumeMatchData(_pID.value!!)
        }
    }
}

