package com.example.capstoneproject.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("message")
    val message: String? = null
)
