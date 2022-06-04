package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.ClassifyResponse
import com.c22ps104.heypetanimalwelfare.api.LoginResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanViewModel : ViewModel() {

    private val retrofit: ApiService = ApiConfig.getApiServiceModel()

    private val _classifyResult = MutableLiveData<ClassifyResponse>()
    val classifyResult: LiveData<ClassifyResponse> = _classifyResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun classify(file:File) {
        val reqFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val reqBody = MultipartBody.Part.createFormData("file", file.name, reqFile)

        _isLoading.postValue(true)
        retrofit.classify(reqBody).enqueue(object :
            Callback<ClassifyResponse> {
            override fun onResponse(call: Call<ClassifyResponse>, response: Response<ClassifyResponse>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _classifyResult.postValue(response.body())
                } else {
                    Log.d("Classify", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ClassifyResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Classify", "Failure ${t.message}")
            }
        })
    }
}