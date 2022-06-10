package com.c22ps104.heypetanimalwelfare.view.main.fragments.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.c22ps104.heypetanimalwelfare.data.UserRepository

class ReminderViewModel(application: Application):AndroidViewModel(application) {
    private val repo = UserRepository(application)

    fun getAllReminder() = repo.getAllReminder()

    suspend fun deleteReminder(idReminder:Int) = repo.deleteReminder(idReminder)

}