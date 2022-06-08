package com.c22ps104.heypetanimalwelfare.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("name") name: String,
        @Field("bio") bio: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("password") password: String,
//        @Part photo: MultipartBody.Part,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun classify(
        @Part file:MultipartBody.Part
    ): Call<ClassifyResponse>

    @Multipart
    @POST("posts/feed")
    fun postFeed(
        @Header("Authorization") token: String,
        @Part ("categoryId") categoryId:RequestBody,
        @Part photo:MultipartBody.Part,
        @Part ("description") desc:RequestBody
    ): Call<PostFeedsResponse>

    @GET("posts")
    fun getAllFeeds(
    ): Call<FeedsResponse>

    @GET("posts/category/{categorizedId}")
    fun getCategorizedFeed(
        @Path ("categorizedId") categorizedId:String
    ): Call<FeedsResponse>
}