package com.capstone.personaway.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/predict")
    fun postText(@Body body: RequestBody): Call<ResponseModel>
}
