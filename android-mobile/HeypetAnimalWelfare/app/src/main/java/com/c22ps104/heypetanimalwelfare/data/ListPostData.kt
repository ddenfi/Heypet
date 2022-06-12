package com.c22ps104.heypetanimalwelfare.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "post")
data class ListPostData(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String
) : Parcelable