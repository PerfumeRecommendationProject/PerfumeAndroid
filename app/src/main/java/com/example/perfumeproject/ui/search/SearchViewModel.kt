package com.example.perfumeproject.ui.search

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
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.detail.PerfumeDetailActivity
import com.example.perfumeproject.ui.recommend_result.RecommendationResultActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : PerfumeViewModel(perfumeRepository) {

    private var _loginWarningDlg: MutableLiveData<Boolean> = MutableLiveData()
    val loginWarningDlg: LiveData<Boolean> = _loginWarningDlg

    private var _pID: MutableLiveData<Int> = MutableLiveData()
    val pID: LiveData<Int> = _pID

    private var _searchVisible: MutableLiveData<Boolean> = MutableLiveData()
    val searchVisible: LiveData<Boolean> = _searchVisible

    private var _startIntent = MutableLiveData<Boolean>()
    val startIntent : LiveData<Boolean> = _startIntent

    val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            showLoading()
            perfumeRepository.getSearchPerfume(p_name = p0.toString(),
                onSuccess = {
                    if (it.isNullOrEmpty()) {
                        _searchVisible.value = false
                    } else {
                        _perfumeData.value = it!!
                        _searchVisible.value = true
                    }
                    hideLoading()
                },
                onFailure = {

                }
            )

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }
    init {
        auth.search = true
        _searchVisible.value = false
        _startIntent.value = false
    }

    override fun perfumeItemClick(perfumeData: PerfumeData) {
        if(perfumeData.isSelected) {
            _pID.value = perfumeData.p_idx
        }
    }

    override fun perfumeLikeClick(perfumeData: PerfumeData) {
        if (auth.token != "guest") {
            perfumeRepository.putScrap(
                scrapRequest = ScrapRequest(perfumeData.p_idx),
                onSuccess = {
                    Timber.d("?????? ????????? ??????")
                },
                onFailure = {
                    Timber.d("?????? ????????? ??????")
                }
            )
        } else {
            _loginWarningDlg.value = true

        }
    }



    fun perfumeRecommendationItem() {
        if(_pID.value != null) {
            _startIntent.value = true
        }

    }
    /** UI ??? onDestroy ???????????? ???????????? ????????? */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
    }
}

