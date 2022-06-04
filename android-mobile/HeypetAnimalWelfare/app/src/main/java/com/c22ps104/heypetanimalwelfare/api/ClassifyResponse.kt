package com.c22ps104.heypetanimalwelfare.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClassifyResponse(

	@field:SerializedName("createdAt")
	val createdAt: String = "",

	@field:SerializedName("personality")
	val personality: String,

	@field:SerializedName("animalType")
	val animalType: String,

	@field:SerializedName("grooming")
	val grooming: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("health")
	val health: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String = ""
) : Parcelable
