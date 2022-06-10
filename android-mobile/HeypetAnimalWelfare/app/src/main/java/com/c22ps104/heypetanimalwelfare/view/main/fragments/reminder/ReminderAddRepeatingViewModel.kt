package com.c22ps104.heypetanimalwelfare.view.main.fragments.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.data.UserRepository
import kotlinx.coroutines.launch

class ReminderAddRepeatingViewModel(application: Application):AndroidViewModel(application) {
    private val repo = UserRepository(application)

    fun addReminder(data:ReminderEntity) = viewModelScope.launch {
        repo.addReminder(data)
    }
}