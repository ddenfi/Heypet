package com.c22ps104.heypetanimalwelfare.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "reminder_table")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val reminderType: Int,
    val reminderName: String,
    val reminderDate: Date
):Parcelable