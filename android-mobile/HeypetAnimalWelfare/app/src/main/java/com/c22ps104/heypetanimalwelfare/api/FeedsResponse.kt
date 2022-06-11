package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class FeedsResponse(

    @field:SerializedName("data")
    val data: FeedsData,

    @field:SerializedName("results")
    val results: Int,

    @field:SerializedName("status")
    val status: String
)

data class PostsItem(

    @field:SerializedName("idUser")
    val idUser: String,

    @field:SerializedName("CategoryId")
    val categoryId: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("userPhoto")
    val userPhoto: String,

    @field:SerializedName("Category")
    val category: FeedsCategory,

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

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class FeedsData(

    @field:SerializedName("posts")
    val posts: List<PostsItem>
)

data class FeedsCategory(

    @field:SerializedName("createdAt")
    val createdAt: Any,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: Any
)
