package com.example.capstoneproject.data.pref

data class UserModel(
    val id: Int = 0,
    val email: String = "",
    val token: String = "",
    val name: String = "",
    val password: String = "",
    val profile: String = "",
    val isLogin: Boolean = false
)