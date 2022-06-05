package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.CategoriesItem
import com.c22ps104.heypetanimalwelfare.api.FeedsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val retrofit: ApiService = ApiConfig.getApiService()

    private val _feedsResult = MutableLiveData<List<CategoriesItem>>()
    val feedsResult: LiveData<List<CategoriesItem>> = _feedsResult

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
                    _feedsResult.postValue(feedsResponse.data.categories)
                } else {
                    Log.d("Get All Feeds", response.body().toString())
                }
            }

            override fun onFailure(call: Call<FeedsResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Get All Feeds", "Failure ${t.message}")
            }
        })
    }
}