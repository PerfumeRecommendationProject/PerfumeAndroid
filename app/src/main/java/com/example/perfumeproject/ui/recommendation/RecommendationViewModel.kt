package com.example.perfumeproject.ui.recommendation

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.data.RecommendationConstant
import com.example.perfumeproject.di.AuthManager
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.ui.detail.PerfumeDetailActivity
import com.example.perfumeproject.ui.recommend_result.RecommendationResultActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : BaseViewModel(perfumeRepository) {

    private var _searchDesc: MutableLiveData<String> = MutableLiveData()
    var searchDesc: LiveData<String> = _searchDesc

    val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _searchDesc.value = p0.toString()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun perfumeRecommendationItem() {

        Intent(PerfumeApplication.appContext, RecommendationResultActivity::class.java).apply {
            putExtra("mode",RecommendationConstant.NEW)
            putExtra("desc", _searchDesc.value)
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

