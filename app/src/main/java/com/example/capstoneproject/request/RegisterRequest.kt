package com.example.capstoneproject.request

data class RegisterRequest(
    val nama: String,
    val username: String,
    val email: String,
    val password: String,
    val password_confirm: String
)