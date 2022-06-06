package com.c22ps104.heypetanimalwelfare.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val reminderName:String,
    val reminderDate:String,
    val reminderTime:String
)