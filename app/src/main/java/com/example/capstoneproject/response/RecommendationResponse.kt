package com.example.capstoneproject.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("rencana")
	val rencana: Rencana? = null,

	@field:SerializedName("randomPlaces")
	val randomPlaces: List<RandomPlacesItem>
)

data class TempatWisata(

	@field:SerializedName("predicted_rating")
	val predictedRating: Double? = null,

	@field:SerializedName("nama_destinasi")
	val namaDestinasi: String? = null,

	@field:SerializedName("distance_km")
	val distanceKm: Double? = null,

	@field:SerializedName("letak_provinsi")
	val letakProvinsi: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("average_rating")
	val averageRating: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class RandomPlacesItem(

	@field:SerializedName("tempatWisata")
	val tempatWisata: TempatWisata? = null,

	@field:SerializedName("tanggal_perencanaan")
	val tanggalPerencanaan: String? = null
)

data class Rencana(

	@field:SerializedName("categoryWisata_id")
	val categoryWisataId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("provinsi_id")
	val provinsiId: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("budget")
	val budget: String? = null
)

data class RecommendationRequest(
	val nama: String,
	val budget: String,
	val provinsi_id: Int,
	val categoryWisata_id: Int,
	val tanggal_perencanaan: String
)

