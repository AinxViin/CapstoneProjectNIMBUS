package com.example.capstoneproject.response

data class WisataResponse(
    val id: Int,
    val nama: String,
    val deskripsi: String?,
    val provinsi_id: Int,
    val categoryWisata_id: Int,
    val thumbnail: String,
    val alamat: String,
    val average_rating: String,
    val google_link: String,
    val latitude: String,
    val longitude: String,
    val review_total: String,
    val website: String?
)
