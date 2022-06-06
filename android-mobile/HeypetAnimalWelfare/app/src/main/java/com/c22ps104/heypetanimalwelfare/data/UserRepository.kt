package com.c22ps104.heypetanimalwelfare.data

import android.app.Application
import com.c22ps104.heypetanimalwelfare.database.UserDatabase

class UserRepository(application: Application) {
    //init
    private val userDatabase = UserDatabase.getDatabase(application)
    private val reminderDao = userDatabase.reminderDao()


    suspend fun addReminder(data:ReminderEntity) {
        reminderDao.addReminder(data)
    }

    fun getAllReminder(){
        reminderDao.readAllReminder()
    }

}