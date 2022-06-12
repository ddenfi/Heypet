package com.c22ps104.heypetanimalwelfare.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM reminder_table ORDER BY reminderDate DESC")
    fun readAllReminder(): LiveData<List<ReminderEntity>>

    @Query("SELECT * FROM reminder_table ORDER BY id DESC LIMIT 1")
    fun readLatestReminder(): LiveData<ReminderEntity>

    @Query("DELETE FROM reminder_table WHERE id = :idReminder")
    suspend fun deleteReminder(idReminder: Int)

    @Query("DELETE FROM reminder_table")
    suspend fun deleteAllReminder()
}