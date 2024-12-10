package com.example.capstoneproject.retrofit

import com.example.capstoneproject.request.AddPlanRequest
import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.response.AddPlanResponse
import com.example.capstoneproject.response.LoginResponse
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.response.ProvinceResponse
import com.example.capstoneproject.response.RecommendationRequest
import com.example.capstoneproject.response.RecommendationResponse
import com.example.capstoneproject.response.RegisterResponse
import com.example.capstoneproject.response.UpdateResponse
import com.example.capstoneproject.response.UserResponse
import com.example.capstoneproject.response.WisataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

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

    @GET("api/user/provinsi")
    suspend fun getProvinces(): List<ProvinceResponse>

    @GET("api/user/tempat-wisata")
    suspend fun getWisata(
        @Query("page") page: Int,
        @Query("category_id") categoryId: Int
    ): List<WisataResponse>

    @POST("api/user/rencana-manual")
    suspend fun addPlan(@Body request: AddPlanRequest): AddPlanResponse

    @GET("api/user/rencanaku-manual")
    suspend fun getPlans(): List<PlanResponse>

    @POST("api/user/rencana-otomatis")
    suspend fun getRecommendations(
        @Body request: RecommendationRequest
    ): RecommendationResponse

    @GET("api/user/category-wisata")
    suspend fun getCategoryWisata(): List<WisataCategoryResponseItem>

}