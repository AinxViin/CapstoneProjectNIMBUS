package com.example.capstoneproject.response

data class WisataToPlanResponse(
    val id: Int,
    val tanggal_perencanaan: String,
    val tempatWisata_id: Int,
    val perencanaanManual_id: Int
)