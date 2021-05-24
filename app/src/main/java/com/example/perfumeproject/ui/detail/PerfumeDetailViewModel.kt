package com.example.perfumeproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.data.request.ScrapRequest
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PerfumeDetailViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : BaseViewModel(perfumeRepository) {

    private var _loginWarningDlg : MutableLiveData<Boolean> = MutableLiveData()
    val loginWarningDlg : LiveData<Boolean> = _loginWarningDlg

    private var _wineLike: MutableLiveData<Boolean> = MutableLiveData()
    var wineLike: LiveData<Boolean> = _wineLike

    private var _perfumeData : MutableLiveData<PerfumeData> = MutableLiveData()
    val perfumeData : LiveData<PerfumeData> = _perfumeData

    /** 생성자 */
    init {
    }

    fun getPerfumeData(perfumeData: PerfumeData) {
        _perfumeData.value = perfumeData
        _wineLike.value = perfumeData.likeYn
    }

    fun perfumeLikeClick() {
        if (auth.token != "guest") {
            perfumeRepository.putScrap(
                scrapRequest = ScrapRequest(_perfumeData.value!!.p_idx),
                onSuccess = {
                   _wineLike.value = !_wineLike.value!!
                    Timber.e("${_wineLike.value}")
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
    }
}

