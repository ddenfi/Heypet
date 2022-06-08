package com.c22ps104.heypetanimalwelfare.view.upload

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.LoginResponse
import com.c22ps104.heypetanimalwelfare.api.PostFeedsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel:ViewModel() {
    private val retrofit: ApiService = ApiConfig.getApiService()

    fun upload(token:String,category:RequestBody,photo:MultipartBody.Part,desc:RequestBody): LiveData<String> {
        val result = MutableLiveData<String>()
        Log.d("Token",token)
        retrofit.postFeed("Bearer $token",category,photo,desc).enqueue(object :
            Callback<PostFeedsResponse> {
            override fun onResponse(call: Call<PostFeedsResponse>, response: Response<PostFeedsResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    result.postValue("Success")
                    Log.d("Upload",responseBody!!.status)
                } else {
                    result.postValue("Filed")
                    Log.d("Upload Filed", response.message())
                }
            }

            override fun onFailure(call: Call<PostFeedsResponse>, t: Throwable) {
                result.postValue("Failure ${t.message}")
                Log.d("Upload Filed", "Failure ${t.message}")
            }
        })

        return result
    }
}