package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData,

	@field:SerializedName("status")
	val status: String
)

data class LoginData(

	@field:SerializedName("user")
	val user: LoginUser,

	@field:SerializedName("token")
	val token: LoginToken
)

data class LoginToken(

	@field:SerializedName("accessTokenExpires")
	val accessTokenExpires: String,

	@field:SerializedName("accessToken")
	val accessToken: String
)

data class LoginUser(

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
	val pet: Any,

	@field:SerializedName("email")
	val email: String
)
