package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.UpdateProfileResponse
import com.c22ps104.heypetanimalwelfare.api.UserDetailResponse
import com.c22ps104.heypetanimalwelfare.api.UserDetailUsers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofit = ApiConfig.getApiService()

    private val _userDetails = MutableLiveData<UserDetailUsers>()
    val userDetails: LiveData<UserDetailUsers> = _userDetails

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
                    Log.d("Edit Profile", response.message())
                    result.postValue("Success")
                } else {
                    Log.d("Edit Profile", response.message())
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                Log.d("Edit Profile", "Failure ${t.message}")
                result.postValue("Failed")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })

        return result
    }

    fun getUserDetail(token: String) {
        retrofit.getUserDetail("Bearer $token").enqueue(object :
            Callback<UserDetailResponse> {

            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val userDetail = response.body()!!
                    _userDetails.postValue(userDetail.data.users)
                    Log.d("Edit Profile", response.message())
                } else {
                    Log.d("Edit Profile", response.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.d("Edit Profile", "Failure ${t.message}")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })
    }
}