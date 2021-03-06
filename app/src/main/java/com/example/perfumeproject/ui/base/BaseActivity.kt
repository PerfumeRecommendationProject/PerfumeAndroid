package com.example.perfumeproject.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.perfumeproject.di.AuthManager
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

/**
 * BaseActivity
 *
 * @param layoutResId 레이아웃 Resource Id
 *
 * @property binding 바인딩 객체
 * @property viewModel ViewModel 객체
 * @property viewModelFactory ViewModel 을 사용하기 위해 반드시 필요한 ViewModelFactory
 *
 * @since v1.0.0 / 2021.01.17
 */


abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    protected lateinit var binding: B



    /**
     * [Dispatchers.Main]을 기본으로 사용하고
     * [onDestroy]에서 [cancel][CoroutineScope.cancel] 되는 코루틴 스코프
     *
     * @see [Coroutine 공식문서 Coroutine scope](https://kotlinlang.org/docs/reference/coroutines/coroutine-context-and-dispatchers.html#coroutine-scope)
     */
    val uiScope: CoroutineScope = MainScope()

    /**
     * [startActivityForResult] 로부터 결과가 올 때까지
     * thread 를 방해하지 않고 결과를 기다리는 [CompletableDeferred] 객체
     *
     * complete or cancel 을 통하여 일을 마칠 수 있으며 자세한 내용은 [CompletableDeferred] 참고
     */
    internal var deferred = CompletableDeferred<ActivityResult>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    abstract val viewModel: BaseViewModel?

    @Inject
    lateinit var authManager: AuthManager
    internal val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e("로그인 실패- $error")
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                val kakaoId = user!!.id
                viewModel?.addKakaoUser(token.accessToken, kakaoId)
            }
            Timber.d("로그인성공 - 토큰 ${authManager.token}")
        }
    }

    internal val baseCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Timber.e("error - ${error}, token- ${token}")
        if (error != null) {
            Timber.e("로그인 실패 ${error}")
        } else if (token != null) {
            //Login Success
            Timber.d("로그인 성공")
            authManager.apply {
                this.token = token.accessToken
            }
            UserApiClient.instance.me { user, error ->
                val kakaoId = user!!.id
                viewModel?.addKakaoUser(token.accessToken, kakaoId)
            }
            Timber.d("로그인성공 - 토큰 ${authManager.token}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        /** [uiScope] 사용 예 */
        uiScope.launch { }

        // LoadingAnimation 통제
        viewModel?.loadingVisible?.observe(this, {
            if (it)
                LoadingAnimation.show(this)
            else
                LoadingAnimation.dismiss()
        })


        viewModel?.loginIntent?.observe(this, {
            startActivity(it)
            finish()
        })
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode : $requestCode, resultCode : $resultCode, intent : $intent")
        deferred.complete(ActivityResult(resultCode, data))
    }

    override fun onDestroy() {
        uiScope.cancel()
        super.onDestroy()
    }


    /**
     * startActivityForResult 를 코루틴 형태로 실행한다.
     *
     * @param intent 실행시킬 intent
     * @param requestCode requestCode
     *
     * @return 각 화면의 [onActivityResult] 에서 complete 또는 cancel 처리해야 할 [Deferred] 객체
     * */
    suspend fun startActivity(
        intent: Intent,
        requestCode: Int = -1
    ): ActivityResult {
        // 이전에 중단되었던 작업이 있는 경우 cancel 시켜준다.
        if (deferred.isActive) deferred.cancel()

        deferred = CompletableDeferred()

        if (requestCode < 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)

        return deferred.await()
    }
}
