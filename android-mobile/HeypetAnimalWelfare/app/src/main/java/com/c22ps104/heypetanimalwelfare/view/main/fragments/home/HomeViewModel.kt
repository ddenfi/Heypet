package com.c22ps104.heypetanimalwelfare.view.main.fragments.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.FeedsResponse
import com.c22ps104.heypetanimalwelfare.api.PostsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

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
                Log.d("Get All Feeds", "Failure ${t.message}")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getCategorizedFeed(categoryId: String) {
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
                Log.d("Categorized Feeds", "Failure ${t.message}")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })
    }
}