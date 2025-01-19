package com.prvavaja.grocerease

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/item")  // Replace with your actual endpoint
    fun getItems(): Call<List<BackendItem>>  // Return a BackendResponse object

    @POST("/api/articles/query") // Replace with your actual endpoint
    fun postItem(
        @Body requestBody: RequestBody
    ): Call<List<BackendItem>>
}