package com.c22ps104.heypetanimalwelfare.data

import com.c22ps104.heypetanimalwelfare.api.ApiService
import com.c22ps104.heypetanimalwelfare.database.UserDatabase

class UserRepository(
    private val userDatabase: UserDatabase,
    private val apiService: ApiService,
    private val preferencesHelper: PreferencesHelper
) {
    // TODO
}