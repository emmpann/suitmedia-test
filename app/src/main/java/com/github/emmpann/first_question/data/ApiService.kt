package com.github.emmpann.first_question.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getAllUser(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): UserResponse
}