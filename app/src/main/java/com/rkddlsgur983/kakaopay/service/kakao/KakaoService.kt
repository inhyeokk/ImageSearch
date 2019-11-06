package com.rkddlsgur983.kakaopay.service.kakao

import com.rkddlsgur983.kakaopay.model.SearchImage
import io.reactivex.Single
import retrofit2.http.*

interface KakaoService {

    @GET("/v2/search/image")
    fun findImages(
        @Query("query")
        query: String,

        @Query("sort")
        sort: String,

        @Query("page")
        page: Int,

        @Query("size")
        size: Int
    ): Single<SearchImage>
}