package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
    val data: RegisterData,

    @field:SerializedName("status")
    val status: String
)

data class RegisterUser(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("bio")
    val bio: String,

    @field:SerializedName("photo")
    val photo: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class RegisterData(

    @field:SerializedName("user")
    val user: RegisterUser,

    @field:SerializedName("token")
    val token: RegisterToken
)

data class RegisterToken(

    @field:SerializedName("accessTokenExpires")
    val accessTokenExpires: String,

    @field:SerializedName("accessToken")
    val accessToken: String
)
