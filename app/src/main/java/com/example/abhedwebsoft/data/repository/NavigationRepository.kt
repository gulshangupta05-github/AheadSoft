package com.example.abhedwebsoft.data.repository

import com.example.abhedwebsoft.data.api.RetrofitClient
import com.example.abhedwebsoft.data.model.UserResponse
import retrofit2.Response
class NavigationRepository {
    private val api = RetrofitClient.apiService

    suspend fun fetchMenuData(): Response<UserResponse> {
        return api.getNavigationData()
    }
}