package com.example.capstoneproject.request

data class AddWisataRequest (
    val tanggal_perencanaan: String? = null,
    val perencanaanManual_id: Int,
    val tempatWisata_id: Int
)