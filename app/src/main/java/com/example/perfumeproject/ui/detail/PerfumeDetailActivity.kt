package com.example.perfumeproject.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.perfumeproject.R
import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.databinding.ActivityHomeBinding
import com.example.perfumeproject.databinding.ActivityPerfumeDetailBinding
import com.example.perfumeproject.ui.base.BaseActivity
import com.example.perfumeproject.ui.home.HomeViewModel
import com.example.perfumeproject.ui.scrap.ScrapAdapter
import com.example.perfumeproject.util.ConfirmDialog
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfumeDetailActivity : BaseActivity<ActivityPerfumeDetailBinding>(R.layout.activity_perfume_detail) {
    override val viewModel: PerfumeDetailViewModel by viewModels<PerfumeDetailViewModel>()
    private val noteAdapter: NoteAdapter by lazy { NoteAdapter(viewModel) }

    private val perfumeData: PerfumeData?
        get() = intent.getSerializableExtra("perfumeData") as? PerfumeData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(com.example.perfumeproject.BR.vm, viewModel)

        perfumeData?.let { viewModel.getPerfumeData(it) } ?: finish()

        binding.rvPerfumeNote.apply {
            adapter = noteAdapter
        }

        viewModel.loginWarningDlg.observe(this, Observer {
            if (it) {
                ConfirmDialog(
                    title = "로그인 하시겠습니까?",
                    content = "스크랩 기능은\n로그인이 필요합니다.",
                    leftText = getString(R.string.test_warning_btn_left_text),
                    leftClickListener = {
                        it.dismiss()
                    },
                    rightText = getString(R.string.test_warning_btn_right_text),
                    rightClickListener = {
                        UserApiClient.instance.run {
                            if (isKakaoTalkLoginAvailable(this@PerfumeDetailActivity)) {
                                loginWithKakaoTalk(this@PerfumeDetailActivity, callback = baseCallback)
                            } else {
                                loginWithKakaoAccount(this@PerfumeDetailActivity, callback = baseCallback)
                            }
                        }
                        it.dismiss()

                    },
                    cancelable = false
                ).show(supportFragmentManager, "LoginWarningDialog")
            }
        })

    }
}