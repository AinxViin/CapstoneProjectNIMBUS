package com.example.capstoneproject.response

import com.example.capstoneproject.data.DestinationDetail

data class PlanDestinationResponse(
    val id: Int,
    val nama: String,
    val budget: String?,
    val user_id: Int,
    val categoryWisata_id: Int?,
    val provinsi_id: Int?,
    val created_at: String,
    val tw_perencanaan_manual: List<DestinationDetail>
)