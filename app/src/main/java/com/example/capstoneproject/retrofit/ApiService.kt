package com.example.capstoneproject.retrofit

import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.response.LoginResponse
import com.example.capstoneproject.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}