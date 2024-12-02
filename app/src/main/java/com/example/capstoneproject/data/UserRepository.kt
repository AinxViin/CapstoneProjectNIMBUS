package com.example.capstoneproject.data

import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.response.ErrorResponse
import com.example.capstoneproject.response.LoginResponse
import com.example.capstoneproject.response.RegisterResponse
import com.example.capstoneproject.response.UpdateResponse
import com.example.capstoneproject.response.UserResponse
import com.example.capstoneproject.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
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

    suspend fun getUser(): UserResponse {
        return try {
            apiService.getData()
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception(errorResponse.message)
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
        userPreference.saveSessions(user)
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
