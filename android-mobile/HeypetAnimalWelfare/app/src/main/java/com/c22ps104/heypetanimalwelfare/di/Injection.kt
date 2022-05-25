package com.c22ps104.heypetanimalwelfare.di

import android.content.Context
import com.c22ps104.heypetanimalwelfare.api.ApiConfig
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.UserRepository
import com.c22ps104.heypetanimalwelfare.database.UserDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val preferencesHelper = PreferencesHelper(context)
        val apiService = ApiConfig.getApiService()

        return UserRepository(database, apiService, preferencesHelper)
    }
}