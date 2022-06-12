package com.c22ps104.heypetanimalwelfare.view.signup

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.api.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofit: ApiService = ApiConfig.getApiService()

    private val _register = MutableLiveData<RegisterResponse>()
    val register: LiveData<RegisterResponse> = _register

    fun register(name: String, bio: String, phoneNumber: String, email: String, password: String) {
        Log.d("registerForm", "$name $email $password $bio $phoneNumber")

        retrofit.register(name, bio, email, phoneNumber, password)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        _register.postValue(responseBody!!)
                    } else {
                        Log.d("Register3", response.body().toString())
                        Toast.makeText(
                            getApplication(),
                            "Email is already registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.d("Register", "Failure ${t.message}")
                    Toast.makeText(getApplication(), "No connection", Toast.LENGTH_SHORT).show()
                }
            })
    }
}