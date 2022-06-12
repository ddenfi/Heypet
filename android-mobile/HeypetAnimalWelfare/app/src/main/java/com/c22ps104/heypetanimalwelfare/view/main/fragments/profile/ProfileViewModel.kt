package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps104.heypetanimalwelfare.api.*
import com.google.firebase.firestore.auth.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val retrofit: ApiService = ApiConfig.getApiService()

    private val _feedsResult = MutableLiveData<List<PostsItem>>()
    val feedsResult: LiveData<List<PostsItem>> = _feedsResult

    private val _userDetails = MutableLiveData<UserDetailUsers>()
    val userDetails: LiveData<UserDetailUsers> = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(token: String) {
        _isLoading.postValue(true)

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
                    Log.d("ProfileDetail", response.message())
                } else {
                    Log.d("ProfileMessage", response.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("ProfileMessage", "Failure ${t.message}")
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
                    Log.d("Get All Feeds", response.message())
                } else {
                    Log.d("Get All Feeds", response.body().toString())
                }
            }

            override fun onFailure(call: Call<FeedsResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Get All Feeds Failed", "Failure ${t.message}")
            }
        })
    }

}