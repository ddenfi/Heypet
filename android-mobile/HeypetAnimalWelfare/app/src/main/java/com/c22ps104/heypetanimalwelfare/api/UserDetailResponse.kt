package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("data")
	val data: UserDetailData,

	@field:SerializedName("status")
	val status: String
)

data class UserDetailData(

	@field:SerializedName("users")
	val users: UserDetailUsers
)

data class UserDetailUsers(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("pet")
	val pet: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
