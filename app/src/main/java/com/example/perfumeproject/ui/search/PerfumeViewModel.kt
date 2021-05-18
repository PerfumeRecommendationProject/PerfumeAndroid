package com.example.perfumeproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.ui.base.BaseViewModel
import timber.log.Timber

abstract class PerfumeViewModel constructor(
    perfumeRepository: PerfumeRepository
) : BaseViewModel(perfumeRepository) {
    /** 검색 결과 list */

    protected val _perfumeData = MutableLiveData<List<PerfumeData>>(listOf())
    open val perfumeData: LiveData<List<PerfumeData>> = _perfumeData

    /** questionList 아이템뷰를 클릭시 동작하는 로직 */
    abstract fun perfumeItemClick(perfumeData: PerfumeData)


    abstract fun perfumeLikeClick(perfumeData: PerfumeData)

    /** [_results] 내 특정 아이템을 추가한다. */
    fun addPerfumeData(perfumeData: PerfumeData) {
        val results: MutableList<PerfumeData> = _perfumeData.value?.toMutableList() ?: mutableListOf()

        results.add(perfumeData)

        _perfumeData.value = results
    }

    /** [_results] 내 특정 아이템을 [wineResult] 변경한다. */
    fun replacePerfumeData(perfumeData: PerfumeData) {
        val results: MutableList<PerfumeData> = _perfumeData.value?.toMutableList() ?: return

        val prevResult = (results.filter { perfumeData.p_name == it.p_name }).firstOrNull()

        prevResult?.let {
            val replaceIndex = results.indexOf(it)

            if (replaceIndex == -1) {
                Timber.i("$it isn't exist")
                return
            } else {
                results.removeAt(replaceIndex)
                results.add(replaceIndex, perfumeData)
                _perfumeData.value = results
            }
        }
    }

    /** [_results] 내 특정 아이템을 삭제한다. */
    fun deletePerfumeData(perfumeData: PerfumeData) {
        val results: MutableList<PerfumeData> = _perfumeData.value?.toMutableList() ?: return

        val deleteIndex = results.indexOf(perfumeData)

        if (deleteIndex == -1) {
            Timber.i("$perfumeData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _perfumeData.value = results
        }
    }

    /** [_results] 내 특정 아이템의 좋아요 클릭 여부 내용만 바꿔준다. */
    fun togglePerfumeData(prevPerfumeData: PerfumeData) {
        val results: MutableList<PerfumeData> = _perfumeData.value?.toMutableList() ?: return

        val replaceIndex = results.indexOf(prevPerfumeData)

        if (replaceIndex == -1) {
            Timber.i("$prevPerfumeData isn't exist")
            return
        } else {
            results.removeAt(replaceIndex)
            results.add(replaceIndex, prevPerfumeData.copy(likeYn = prevPerfumeData.clickedLikeYn))
            _perfumeData.value = results
        }
    }

}
