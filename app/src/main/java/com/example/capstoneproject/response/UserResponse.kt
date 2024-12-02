package com.example.capstoneproject.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("profile_pic")
    val profilePic: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("is_user")
    val isUser: Boolean? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)
