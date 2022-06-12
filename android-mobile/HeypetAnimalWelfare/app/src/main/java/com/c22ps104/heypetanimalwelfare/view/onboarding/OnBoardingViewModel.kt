package com.c22ps104.heypetanimalwelfare.view.onboarding

import android.app.Application
import android.util.Log
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateProfile(token: String, bio: String, pet: String, photo: File): LiveData<String> {
        val result = MutableLiveData<String>()
        val reqFile: RequestBody = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val reqBody = MultipartBody.Part.createFormData("file", photo.name, reqFile)

        _isLoading.postValue(true)

        retrofit.updateProfile("Bearer $token", bio, pet, reqBody).enqueue(object :
            Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                _isLoading.postValue(true)

                if (response.isSuccessful) {
                    result.postValue("Success")
                    Log.d("Register VM", response.message())
                } else {
                    Log.d("Register VM", response.message())
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                Log.d("Register VM", "Failure ${t.message}")
                result.postValue("Filed")
                _isLoading.postValue(true)
            }
        })

        return result
    }
}