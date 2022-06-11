package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class PostFeedsResponse(

	@field:SerializedName("data")
	val data: PostFeedsData,

	@field:SerializedName("status")
	val status: String
)

data class PostFeedsData(

	@field:SerializedName("posts")
	val posts: Posts
)

data class Posts(

	@field:SerializedName("idUser")
	val idUser: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("userPhoto")
	val userPhoto: String,

	@field:SerializedName("idFeeds")
	val idFeeds: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("categoryId")
	val categoryId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
