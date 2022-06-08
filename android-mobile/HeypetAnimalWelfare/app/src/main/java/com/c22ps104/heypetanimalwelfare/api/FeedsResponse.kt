package com.c22ps104.heypetanimalwelfare.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedsResponse(

	@field:SerializedName("data")
	val data: FeedsData,

	@field:SerializedName("results")
	val results: Int,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class PostCategoriesItem(

	@field:SerializedName("CategoryId")
	val categoryId: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable

@Parcelize
data class FeedsData(

	@field:SerializedName("categories")
	val categories: List<PostCategoriesItem>
) : Parcelable
