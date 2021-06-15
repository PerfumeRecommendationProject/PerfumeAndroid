package com.example.perfumeproject.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfumeproject.BuildConfig
import com.example.perfumeproject.PerfumeApplication
import com.example.perfumeproject.data.PerfumeRepository
import com.example.perfumeproject.network.PerfumeService
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.perfumeproject.util.SharedPrefs
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val CONNECT_TIMEOUT = 30.toLong()
const val WRITE_TIMEOUT = 30.toLong()
const val READ_TIMEOUT = 30.toLong()

const val BASE_URL = "http://13.209.121.249/"

/**
 * 코루틴을 활용하여 HTTP 요청을 보낼 시 활용하는 로직
 * 코루틴을 활용할 경우, onFailure 에서 보내는 exception 내용에 따라 로직 작업을 수행한다.
 */
suspend fun <T> Call<T>.send(): Response<T> = suspendCoroutine {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            it.resume(response)
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            it.resumeWithException(throwable)
        }
    })
}

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideViewModelFactory(
        perfumeRepository: PerfumeRepository
    ): ViewModelProvider.AndroidViewModelFactory = ViewModelFactoryImpl(
        PerfumeApplication.getGlobalAppApplication(), perfumeRepository
    )

    /**
     * ViewModelFactory 구현체 (impl) 를 만드는 클래스
     */
    class ViewModelFactoryImpl(
        val perfumeApplication: PerfumeApplication,
        val perfumeRepository: PerfumeRepository
    ) : ViewModelProvider.AndroidViewModelFactory(perfumeApplication) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BaseViewModel(perfumeRepository) as T
        }
    }
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(): Cache {
        // 10MB
        val cacheSize = 10 * 1024 * 1024L
        return Cache(PerfumeApplication.getGlobalApplicationContext().cacheDir, cacheSize)
    }

    /**
     * 커스텀 interceptor
     * Firebase Crashlytic 로깅 로직을 넣을 예정이며 카카오 token 체크가 필요할 시 아래 함수를 활용한다.
     */
    @Provides
    @Singleton
    fun provideWinePickInterceptor(authManager: AuthManager): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            var newUrl = request.url.toString()
            val builder = chain.request().newBuilder()
                .url(newUrl)

            if (newUrl.contains("/perfume/search")) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("token", authManager.serverToken)
                    url(newUrl)
                }.build())
            }

            if (newUrl.contains("/scrap")) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("token", authManager.serverToken)
                    url(newUrl)
                }.build())
            }


            if (newUrl.contains("/perfume/recommend")) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("token", authManager.serverToken)
                    url(newUrl)
                }.build())
            }



            return@Interceptor chain.proceed(builder.build())
        }
    }

    /** HttpClient 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideHttpClient(okHttpCache: Cache, winePickInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(okHttpCache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(winePickInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /** Retrofit 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PerfumeService {
        return retrofit.create(PerfumeService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefs {
        return SharedPrefs()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthManager(sharedPrefs: SharedPrefs): AuthManager {
        return AuthManager(sharedPrefs)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePerfumeRepository(
        perfumeService: PerfumeService,
        authManager: AuthManager
    ): PerfumeRepository {
        return PerfumeRepository(perfumeService, authManager)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

}



