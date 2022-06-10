package com.c22ps104.heypetanimalwelfare.view.main.fragments.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.data.UserRepository

class ReminderAddOneTimeViewModel(application: Application) :AndroidViewModel(application) {
    private val repo = UserRepository(application)

    suspend fun addReminder(data: ReminderEntity) = repo.addReminder(data)

    fun getLatestReminder()= repo.getLatestReminder()
}