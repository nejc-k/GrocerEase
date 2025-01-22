package com.prvavaja.grocerease

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("article/query")
    fun postItem(
        @Query("page") page: Int,
        @Body requestBody: RequestBody
    ): Call<List<BackendItem>>

    @POST("article/compare-prices")
    fun comparePrices(
        @Body requestBody: RequestBody
    ): Call<ApiResponseCompareItems>


}