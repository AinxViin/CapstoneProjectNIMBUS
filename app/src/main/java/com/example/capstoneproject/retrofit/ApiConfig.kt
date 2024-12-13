package com.example.capstoneproject.retrofit

import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.utils.CustomCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://nimbus-443014235398.asia-southeast2.run.app/api/"

    fun apiService(userPreference: UserPreference): ApiService {
        // Create an HttpLoggingInterceptor to log API requests and responses
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs headers, body, etc.
        }

        // Create an OkHttpClient and add the interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cookieJar(CustomCookieJar(userPreference))
            .build()


        // Create a Retrofit instance with the OkHttpClient
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
