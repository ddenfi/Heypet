package com.c22ps104.heypetanimalwelfare.view.main.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps104.heypetanimalwelfare.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val retrofit: ApiService = ApiConfig.getApiService()
    private val _filterState = MutableLiveData("0")

    val filterState: LiveData<String> = _filterState

    fun setFilterState(categoryId: String) {
        _filterState.postValue(categoryId)
    }

    private val _feedsResult = MutableLiveData<List<PostsItem>>()
    val feedsResult: LiveData<List<PostsItem>> = _feedsResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFeeds() {
        Log.d("View Model Called", "")

        _isLoading.postValue(true)

        retrofit.getAllFeeds().enqueue(object :
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

    fun getCategorizedFeed(categoryId: String) {
        Log.d("View Model Called", "")

        retrofit.getCategorizedFeed(categoryId).enqueue(object :
            Callback<FeedsResponse> {
            override fun onResponse(call: Call<FeedsResponse>, response: Response<FeedsResponse>) {
                Log.d("Feed url", call.request().url.toString())
                _isLoading.postValue(false)

                if (response.isSuccessful) {
                    val feedsResponse: FeedsResponse = response.body()!!
                    _feedsResult.postValue(feedsResponse.data.posts)
                    Log.d("Categorized Feeds", response.message())
                } else {
                    Log.d("Categorized Feeds", response.message())
                }
            }

            override fun onFailure(call: Call<FeedsResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Feeds Categorize Failed", "Failure ${t.message}")
            }
        })
    }
}