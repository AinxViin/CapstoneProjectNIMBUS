package com.example.capstoneproject.response

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)