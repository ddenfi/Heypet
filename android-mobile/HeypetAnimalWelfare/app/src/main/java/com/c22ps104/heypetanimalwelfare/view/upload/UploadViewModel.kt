package com.c22ps104.heypetanimalwelfare.view.upload

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.PostFeedsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofit: ApiService = ApiConfig.getApiService()

    fun upload(
        token: String,
        category: RequestBody,
        photo: MultipartBody.Part,
        desc: RequestBody
    ): LiveData<String> {
        val result = MutableLiveData<String>()

        retrofit.postFeed("Bearer $token", category, photo, desc).enqueue(object :
            Callback<PostFeedsResponse> {
            override fun onResponse(
                call: Call<PostFeedsResponse>,
                response: Response<PostFeedsResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.d("Upload", responseBody!!.status)
                    result.postValue("Success")
                } else {
                    Log.d("Upload", response.message())
                    result.postValue("Failed")
                }
            }

            override fun onFailure(call: Call<PostFeedsResponse>, t: Throwable) {
                Log.d("Upload", "Failure ${t.message}")
                result.postValue("Failed")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })

        return result
    }
}