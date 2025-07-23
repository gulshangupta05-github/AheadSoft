package com.example.abhedwebsoft.data.api

import com.example.abhedwebsoft.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("navigation")
    suspend fun getNavigationData(
        @Query("restApi") restApi: String = "Sesapi",
        @Query("sesapi_platform") platform: Int = 1,
        @Query("auth_token") token: String = "B179086bb56c32731633335762"
    ): Response<UserResponse>
}