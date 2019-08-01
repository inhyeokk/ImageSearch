package com.rkddlsgur983.kakaopay.api

import com.rkddlsgur983.kakaopay.model.SearchImage
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("/v2/search/image")
    fun findImages(@Query("query") query: String): Observable<SearchImage>
}