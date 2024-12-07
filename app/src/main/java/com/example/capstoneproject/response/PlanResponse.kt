package com.example.capstoneproject.response

data class PlanResponse(
    val id: Int,
    val nama: String,
    val budget: String?,
    val user_id: Int,
    val categoryWisata_id: Int?,
    val provinsi_id: Int?,
    val created_at: String,
    val provinsi: String?,
    val categoryWisata: String?
)
