package com.c22ps104.heypetanimalwelfare.api

import com.google.gson.annotations.SerializedName

data class CategorizedFeedsResponse(

	@field:SerializedName("data")
	val data: FilterData,

	@field:SerializedName("results")
	val results: Int,

	@field:SerializedName("status")
	val status: String
)

data class FilterCategoriesItem(

	@field:SerializedName("CategoryId")
	val categoryId: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("Category")
	val category: Category,

	@field:SerializedName("idFeeds")
	val idFeeds: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class FilterData(

	@field:SerializedName("categories")
	val categories: List<FilterCategoriesItem>
)

data class Category(

	@field:SerializedName("createdAt")
	val createdAt: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: Any
)
