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

    val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            perfumeRepository.getSearchPerfume(p_name = p0.toString(),
                onSuccess = {
                    _perfumeData.value = it
                },
                onFailure = {

                }
            )

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }
    override fun perfumeItemClick(perfumeData: PerfumeData) {
        _pID.value = perfumeData.p_idx
    }

    override fun perfumeLikeClick(perfumeData: PerfumeData) {
        if (auth.token != "guest") {
            perfumeRepository.putScrap(
                scrapRequest = ScrapRequest(perfumeData.p_idx),
                onSuccess = {
                    Timber.d("향수 스크랩 성공")
                },
                onFailure = {
                    Timber.d("향수 스크랩 성공")
                }
            )
        } else {
            _loginWarningDlg.value = true

        }
    }

    fun perfumeRecommendationItem() {

        Intent(PerfumeApplication.appContext, RecommendationResultActivity::class.java).apply {
            putExtra("mode", RecommendationConstant.BASE)
            putExtra("pId", _pID.value)
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
    }
}

