package com.example.capstoneproject.data

data class DestinationDetail(
    val id: Int,
    val tanggal_perencanaan: String,
    val tempatWisata_id: Int,
    val perencanaanManual_id: Int,
    val tempatWisata: WisataDetail
)