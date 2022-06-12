package com.c22ps104.heypetanimalwelfare.view.onboarding

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.UpdateProfileResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofit = ApiConfig.getApiService()

    fun updateProfile(
        token: String,
        pet: RequestBody,
        bio: RequestBody,
        photo: File
    ): LiveData<String> {
        val result = MutableLiveData<String>()
        val reqFile: RequestBody = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val reqBody = MultipartBody.Part.createFormData("file", photo.name, reqFile)

        retrofit.updateProfile("Bearer $token", pet, bio, reqBody).enqueue(object :
            Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("On Boarding", response.message())
                    result.postValue("Success")
                } else {
                    Log.d("On Boarding", response.message())
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                Log.d("On Boarding", "Failure ${t.message}")
                result.postValue("Failed")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })

        return result
    }
}