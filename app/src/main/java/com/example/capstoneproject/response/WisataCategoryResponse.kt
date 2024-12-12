package com.example.capstoneproject.response

import com.google.gson.annotations.SerializedName

data class WisataCategoryResponse(

	@field:SerializedName("WisataCategoryResponse")
	val wisataCategoryResponse: List<WisataCategoryResponseItem>
)

data class WisataCategoryResponseItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
