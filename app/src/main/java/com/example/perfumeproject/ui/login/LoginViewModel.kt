package com.example.perfumeproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    perfumeRepository: PerfumeRepository
) : BaseViewModel(perfumeRepository) {
    private var _login = MutableLiveData<String>()
    var login: LiveData<String> = _login

    /** 생성자 */
    init {
        _login.value = MutableLiveData<String>().value
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
