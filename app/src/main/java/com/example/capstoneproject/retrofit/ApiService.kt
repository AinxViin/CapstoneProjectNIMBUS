package com.example.capstoneproject.retrofit

import com.example.capstoneproject.request.AddPlanRequest
import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.request.RekomendasiRequest
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.request.WisataToPlanRequest
import com.example.capstoneproject.response.AddPlanResponse
import com.example.capstoneproject.response.DetailWisataResponse
import com.example.capstoneproject.response.LoginResponse
import com.example.capstoneproject.response.PlanDestinationResponse
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.response.ProvinceResponse
import com.example.capstoneproject.response.RecommendationRequest
import com.example.capstoneproject.response.RecommendationResponse
import com.example.capstoneproject.response.RegisterResponse
import com.example.capstoneproject.response.UpdateResponse
import com.example.capstoneproject.response.UserResponse
import com.example.capstoneproject.response.WisataAlamResponse
import com.example.capstoneproject.response.WisataCategoryResponseItem
import com.example.capstoneproject.response.WisataHiburanResponse
import com.example.capstoneproject.response.WisataResponse
import com.example.capstoneproject.response.WisataSeniResponse
import com.example.capstoneproject.response.WisataToPlanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @PUT("user/info")
    suspend fun updateInfo(
        @Body updateRequest: UpdateRequest
    ): UpdateResponse

    @GET("user")
    suspend fun getData(): UserResponse

    @GET("user/provinsi")
    suspend fun getProvinces(): List<ProvinceResponse>

    @GET("user/category-wisata")
    suspend fun getCategoryWisata(): List<WisataCategoryResponseItem>

    @GET("user/tempat-wisata")
    suspend fun getWisata(
        @Query("page") page: Int,
        @Query("category_id") categoryId: Int
    ): List<WisataResponse>

    @POST("user/rencana-manual")
    suspend fun addPlan(@Body request: AddPlanRequest): AddPlanResponse

    @GET("user/rencanaku-manual")
    suspend fun getPlans(): List<PlanResponse>

    @POST("user/rencana-otomatis")
    suspend fun getRecommendations(
        @Body request: RecommendationRequest
    ): RecommendationResponse

    @GET("user/rencanaku-manual-destinasi/{id}")
    suspend fun getPlanDestinations(@Path("id") planId: Int): List<PlanDestinationResponse>

    @DELETE("user/rencana-wisata-manual/{planId}/{wisataId}")
    suspend fun deleteDestination(
        @Path("planId") planId: Int,
        @Path("wisataId") wisataId: Int
    ): Response<Unit>

    @DELETE("user/rencana-manual/{planId}")
    suspend fun deletePlan(@Path("planId") planId: Int): Response<Unit>

    @GET("user/tempat-wisata/{id}")
    suspend fun getDetailWisata(@Path("id") id: Int): Response<DetailWisataResponse>

    @POST("user/rencana-wisata-manual")
    suspend fun addWisatatoPlan(@Body request: WisataToPlanRequest): WisataToPlanResponse

    @GET("user/tempat-wisata/category/wisata_alam")
    suspend fun getWisataAlam(): Response<WisataAlamResponse>

    @GET("user/tempat-wisata/category/hiburan")
    suspend fun getWisataHiburan(): Response<WisataHiburanResponse>

    @GET("user/tempat-wisata/category/seni_dan_budaya")
    suspend fun getWisataSeni(): Response<WisataSeniResponse>

    @POST("user/recommend-destinations")
    suspend fun postRekomendasiWisata(@Body request: RekomendasiRequest): List<WisataResponse>


}