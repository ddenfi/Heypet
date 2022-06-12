package com.c22ps104.heypetanimalwelfare.data

import android.app.Application
import com.c22ps104.heypetanimalwelfare.database.UserDatabase

class UserRepository(application: Application) {

    // Initiation
    private val userDatabase = UserDatabase.getDatabase(application)
    private val reminderDao = userDatabase.reminderDao()

    // Reminder
    suspend fun addReminder(data: ReminderEntity) {
        reminderDao.addReminder(data)
    }

    fun getAllReminder() = reminderDao.readAllReminder()

    fun getLatestReminder() = reminderDao.readLatestReminder()

    suspend fun deleteAllReminder() = reminderDao.deleteAllReminder()

    suspend fun deleteReminder(idReminder: Int) = reminderDao.deleteReminder(idReminder)
}