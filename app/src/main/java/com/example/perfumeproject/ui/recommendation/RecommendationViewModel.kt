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
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val auth: AuthManager,
) : BaseViewModel(perfumeRepository) {

    private var _searchDesc: MutableLiveData<String> = MutableLiveData()
    val searchDesc: LiveData<String> = _searchDesc


    private var _warningEng = MutableLiveData<Boolean>()
    val warningEng : LiveData<Boolean> = _warningEng


    private var _startIntent = MutableLiveData<Boolean>()
    val startIntent : LiveData<Boolean> = _startIntent

    val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                val ps: Pattern = Pattern.compile("^[a-zA-Z0-9\\s!~`@#\$%\\^?,. ]+$")
                _warningEng.value = !ps.matcher(p0).matches()
                _searchDesc.value = p0.toString()
            } else {
                _warningEng.value = false
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }
    init {
        _warningEng.value = false
        _startIntent.value = false
    }

    fun perfumeRecommendationItem() {

        if(!_warningEng.value!!) {
            _startIntent.value = true
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

