package com.example.capstoneproject.response

import com.google.gson.annotations.SerializedName

data class RekomendasiWisataResponse(

	@field:SerializedName("RekomendasiWisataResponse")
	val rekomendasiWisataResponse: List<WisataResponse>
)