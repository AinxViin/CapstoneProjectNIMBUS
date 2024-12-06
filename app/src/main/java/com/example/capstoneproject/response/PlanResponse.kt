package com.example.capstoneproject.response

data class PlanResponse(
    val id: Int,
    val nama: String,
    val budget: Int? = null,
    val user_id: Int,
    val categoryWisata_id: Int? = null,
    val provinsi_id: Int? = null,
    val created_at: String
)