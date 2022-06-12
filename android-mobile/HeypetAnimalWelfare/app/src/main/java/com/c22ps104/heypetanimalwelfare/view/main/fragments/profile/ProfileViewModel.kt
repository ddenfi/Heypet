package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofit: ApiService = ApiConfig.getApiService()

    private val _feedsResult = MutableLiveData<List<PostsItem>>()
    val feedsResult: LiveData<List<PostsItem>> = _feedsResult

    private val _userDetails = MutableLiveData<UserDetailUsers>()
    val userDetails: LiveData<UserDetailUsers> = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(token: String) {
        retrofit.getUserDetail("Bearer $token").enqueue(object :
            Callback<UserDetailResponse> {

            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.postValue(false)

                if (response.isSuccessful) {
                    val userDetail = response.body()!!
                    _userDetails.postValue(userDetail.data.users)
                    Log.d("Profile Detail", response.message())
                } else {
                    Log.d("Profile Detail", response.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Profile Detail", "Failure ${t.message}")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getUserPost(idUser: String) {
        _isLoading.postValue(true)

        retrofit.getUserPost(idUser).enqueue(object :
            Callback<FeedsResponse> {
            override fun onResponse(call: Call<FeedsResponse>, response: Response<FeedsResponse>) {
                _isLoading.postValue(false)

                if (response.isSuccessful) {
                    val feedsResponse: FeedsResponse = response.body()!!
                    _feedsResult.postValue(feedsResponse.data.posts)
                    Log.d("Profile Posts", response.message())
                } else {
                    Log.d("Profile Posts", response.body().toString())
                }
            }

            override fun onFailure(call: Call<FeedsResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Profile Posts", "Failure ${t.message}")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

}