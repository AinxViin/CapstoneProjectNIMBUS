package com.example.capstoneproject.response

import com.example.capstoneproject.data.CategoryWisata
import com.example.capstoneproject.data.Provinsi

data class AddPlanResponse(
    val id: Int,
    val nama: String,
    val budget: String?,
    val user_id: Int,
    val categoryWisata_id: Int?,
    val provinsi_id: Int?,
    val created_at: String,
    val provinsi: Provinsi?,
    val categoryWisata: CategoryWisata?
)