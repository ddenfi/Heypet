package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class PostFeedsResponse(

	@field:SerializedName("data")
	val data: PostData,

	@field:SerializedName("status")
	val status: String
)

data class Categories(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("categoryId")
	val categoryId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class PostData(

	@field:SerializedName("categories")
	val categories: Categories
)
