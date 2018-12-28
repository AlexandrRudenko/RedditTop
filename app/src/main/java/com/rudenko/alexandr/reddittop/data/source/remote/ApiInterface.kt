package com.rudenko.alexandr.reddittop.data.source.remote

import com.rudenko.alexandr.reddittop.data.source.remote.model.ResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top.json")
    fun getTopNews(@Query("after") after: String?,
                   @Query("limit") limit: Int?): Single<ResponseDto>
}