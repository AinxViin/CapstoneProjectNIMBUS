package com.example.capstoneproject.data

import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.request.AddPlanRequest
import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.request.RekomendasiRequest
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.request.WisataToPlanRequest
import com.example.capstoneproject.response.ErrorResponse
import com.example.capstoneproject.response.LoginResponse
import com.example.capstoneproject.response.PlanDestinationResponse
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.response.ProvinceResponse
import com.example.capstoneproject.response.RecommendationRequest
import com.example.capstoneproject.response.RecommendationResponse
import com.example.capstoneproject.response.RegisterResponse
import com.example.capstoneproject.response.UpdateResponse
import com.example.capstoneproject.response.UserResponse
import com.example.capstoneproject.response.WisataCategoryResponseItem
import com.example.capstoneproject.response.WisataResponse
import com.example.capstoneproject.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.register(registerRequest)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return try {
            apiService.login(loginRequest)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception(errorResponse.message)
        }
    }

    suspend fun getRecommendations(request: RecommendationRequest): RecommendationResponse {
        return try {
            apiService.getRecommendations(request)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception(errorResponse.message)
        }
    }


    suspend fun getUser(): UserResponse {
        return try {
            apiService.getData()
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception(errorResponse.message)
        }
    }

    suspend fun getCategoryWisata(): List<WisataCategoryResponseItem> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCategoryWisata()
            response // Assuming `getProvinces` returns a list of ProvinceResponse
        }
    }

    suspend fun updateUser(updateRequest: UpdateRequest): UpdateResponse {
        return try {
            val userData = userPreference.getSession().firstOrNull()
            val token = userData?.token ?: throw Exception("User token is missing")
            apiService.updateInfo(updateRequest)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception(errorResponse.message ?: "Unknown error occurred")
        } catch (e: Exception) {
            throw Exception(e.message ?: "An unexpected error occurred")
        }
    }

    suspend fun saveSession(user: UserModel) {
        if (user.isLogin) {
            userPreference.saveSessions(user)
        } else {
            throw Exception("Failed to save session: User not logged in")
        }
    }

    suspend fun getUserSession(): UserModel? {
        return userPreference.getSession().firstOrNull()
    }

    suspend fun getProvinces(): List<ProvinceResponse> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getProvinces()
            response
        }
    }

    suspend fun getWisata(): List<WisataResponse> = withContext(Dispatchers.IO) {
        val allWisata = mutableListOf<WisataResponse>()
        val totalPages = 93

        try {
            for (page in 1..totalPages) {
                val pageData = apiService.getWisata(categoryId = 1, page = page)
                allWisata.addAll(pageData)

                if (pageData.isEmpty()) break
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch wisata data: ${e.message}")
        }

        return@withContext allWisata
    }

    suspend fun addPlan(addPlanRequest: AddPlanRequest): Boolean {
        return try {
            apiService.addPlan(addPlanRequest)
            true
        } catch (e: Exception) {
            throw Exception("Failed to add plan: ${e.message}")
        }
    }


    suspend fun getUserPlans(): List<PlanResponse> {
        return try {
            apiService.getPlans()
        } catch (e: Exception) {
            throw Exception("Failed to fetch plans: ${e.message}")
        }
    }

    suspend fun getPlanDestinations(planId: Int): List<PlanDestinationResponse> {
        return try {
            apiService.getPlanDestinations(planId)
        } catch (e: Exception) {
            throw Exception("Failed to fetch destinations: ${e.message}")
        }
    }

    suspend fun deleteDestination(planId: Int, wisataId: Int): Boolean {
        return try {
            val response = apiService.deleteDestination(planId, wisataId)
            response.isSuccessful
        } catch (e: Exception) {
            throw Exception("Failed to delete destination: ${e.message}")
        }
    }

    suspend fun addWisataToPlan(request: WisataToPlanRequest): Boolean {
        return try {
            apiService.addWisatatoPlan(request)
            true
        } catch (e: Exception) {
            throw Exception("Failed to add Wisata : ${e.message}")
        }
    }

    suspend fun getWisataAlam(): List<WisataDetail> {
        return try {
            val response = apiService.getWisataAlam()

            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getWisataHiburan(): List<WisataDetail> {
        return try {
            val response = apiService.getWisataHiburan()

            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getWisataSeni(): List<WisataDetail> {
        return try {
            val response = apiService.getWisataSeni()

            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun postRekomendasi(request: RekomendasiRequest): List<WisataResponse> {
        return try {
            apiService.postRekomendasiWisata(request)
        } catch (e: Exception) {
            throw Exception("Failed to add Wisata : ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(apiService, userPreference)
                INSTANCE = instance
                instance
            }
        }
    }
}
