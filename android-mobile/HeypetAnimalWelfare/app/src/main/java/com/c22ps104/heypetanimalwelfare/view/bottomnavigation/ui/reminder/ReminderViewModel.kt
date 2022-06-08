package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps104.heypetanimalwelfare.data.UserRepository

class ReminderViewModel(application: Application):AndroidViewModel(application) {
    private val repo = UserRepository(application)

    fun getAllReminder() = repo.getAllReminder()

}