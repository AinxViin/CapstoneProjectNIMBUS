package com.example.capstoneproject.response

import com.google.gson.annotations.SerializedName

data class AllProvinceResponse(

	@field:SerializedName("AllProvinceResponse")
	val allProvinceResponse: List<AllProvinceResponseItem?>? = null
)

data class AllProvinceResponseItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
