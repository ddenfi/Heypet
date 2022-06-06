package com.c22ps104.heypetanimalwelfare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c22ps104.heypetanimalwelfare.data.ListPostData
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity

@Database(
    entities = [ListPostData::class, RemoteKeys::class,ReminderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}