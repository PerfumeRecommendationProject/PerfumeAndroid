package com.example.perfumeproject.network


import com.example.perfumeproject.data.PerfumeData
import com.example.perfumeproject.data.PerfumeResponse
import com.example.perfumeproject.data.request.KakaoLoginRequest
import com.example.perfumeproject.data.request.ScrapRequest
import com.example.perfumeproject.data.request.perfumeDescRequest
import com.example.perfumeproject.data.response.KakaoLoginResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * 실제 서비스에서 사용하는 Retrofit2 인터페이스
 *
 */
interface PerfumeService {

    @POST("auth/login")
    fun postKakaoLogin(
            @Body kakaoLoginRequest: KakaoLoginRequest
    ): Call<PerfumeResponse<KakaoLoginResponse>>

    @GET("perfume/search")
    fun getSearchPerfume(
            @Query("p_name") searchKeyword : String
    ) : Call<PerfumeResponse<List<PerfumeData>>>

    @PUT("scrap")
    fun putScrap(
        @Body scrapRequest: ScrapRequest
    ) : Call<PerfumeResponse<Unit>>

    @GET("scrap")
    fun getScrapData() : Call<PerfumeResponse<List<PerfumeData>>>

    @POST("perfume/recommend/new")
    fun getNewPerfumeList(
        @Body perfumeDescRequest: perfumeDescRequest
    ) : Call<PerfumeResponse<List<PerfumeData>>>

    @POST ("perfume/recommend/based")
    fun postBasedPerfume(
        @Body scrapRequest: ScrapRequest
    ) : Call<PerfumeResponse<List<PerfumeData>>>
}
