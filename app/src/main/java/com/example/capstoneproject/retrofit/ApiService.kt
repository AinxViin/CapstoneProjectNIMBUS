package com.example.capstoneproject.retrofit

import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.response.LoginResponse
import com.example.capstoneproject.response.RegisterResponse
import com.example.capstoneproject.response.UpdateResponse
import com.example.capstoneproject.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @PUT("api/user/info")
    suspend fun updateInfo(
        @Body updateRequest: UpdateRequest
    ): UpdateResponse

    @GET("api/user")
    suspend fun getData(): UserResponse
}