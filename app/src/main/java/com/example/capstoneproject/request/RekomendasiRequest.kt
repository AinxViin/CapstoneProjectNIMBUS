package com.example.capstoneproject.request

import com.google.gson.annotations.SerializedName

data class RekomendasiRequest(

    @field:SerializedName("selected_place")
    val selectedPlace: String? = null
)
