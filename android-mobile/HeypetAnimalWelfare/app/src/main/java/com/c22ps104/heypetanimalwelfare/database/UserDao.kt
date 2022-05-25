package com.c22ps104.heypetanimalwelfare.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c22ps104.heypetanimalwelfare.data.ListPostData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(story: List<ListPostData>)

    @Query("SELECT * FROM story")
    fun getAllUser(): PagingSource<Int, ListPostData>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}