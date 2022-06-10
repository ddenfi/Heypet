package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("status")
	val status: String
)
