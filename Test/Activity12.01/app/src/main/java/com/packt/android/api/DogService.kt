package com.packt.android.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface DogService {

    @GET("breed/hound/images/random/{number}")
    suspend fun getDogs(@Path("number") number: Int): Dog

    @GET
    suspend fun downloadFile(@Url fileUrl: String): ResponseBody
}