package com.c22ps104.heypetanimalwelfare.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.LoginResponse
import com.c22ps104.heypetanimalwelfare.api.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel:ViewModel() {
    private val retrofit: ApiService = ApiConfig.getApiService()

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    fun login(email: String, password: String) {
        Log.d("Form"," $email $password")
        retrofit.login(email,password).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _login.postValue(response.body())
                } else {
                    Log.d("Login", response.body().toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Register", "Failure ${t.message}")
            }
        })
    }
}