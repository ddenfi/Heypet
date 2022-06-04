package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin,

	@field:SerializedName("status")
	val status: String
)

data class UserLogin(

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

	@field:SerializedName("email")
	val email: String
)

data class TokenLogin(

	@field:SerializedName("accessTokenExpires")
	val accessTokenExpires: String,

	@field:SerializedName("accessToken")
	val accessToken: String
)

data class DataLogin(

	@field:SerializedName("user")
	val user: UserLogin,

	@field:SerializedName("token")
	val token: TokenLogin
)
