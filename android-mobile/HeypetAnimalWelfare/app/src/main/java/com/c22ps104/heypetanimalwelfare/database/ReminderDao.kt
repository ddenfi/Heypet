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
    suspend fun addReminder(reminder:ReminderEntity)

    @Query("SELECT * FROM reminder_table ORDER BY id ASC")
    fun readAllReminder(): LiveData<List<ReminderEntity>>
}