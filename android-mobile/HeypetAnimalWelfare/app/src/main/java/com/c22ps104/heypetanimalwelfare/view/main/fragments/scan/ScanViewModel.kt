package com.c22ps104.heypetanimalwelfare.view.main.fragments.scan

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.ClassifyResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofit: ApiService = ApiConfig.getApiServiceModel()

    private val _classifyResult = MutableLiveData<ClassifyResponse>()
    val classifyResult: LiveData<ClassifyResponse> = _classifyResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun classify(file: File) {
        val reqFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val reqBody = MultipartBody.Part.createFormData("file", file.name, reqFile)

        _isLoading.postValue(true)

        retrofit.classify(reqBody).enqueue(object :
            Callback<ClassifyResponse> {
            override fun onResponse(
                call: Call<ClassifyResponse>,
                response: Response<ClassifyResponse>
            ) {
                _isLoading.postValue(false)

                if (response.isSuccessful) {
                    _classifyResult.postValue(response.body())
                } else {
                    Log.d("Scan", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ClassifyResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("Scan", "Failure ${t.message}")
                Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
            }
        })
    }
}