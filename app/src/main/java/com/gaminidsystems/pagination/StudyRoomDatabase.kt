package com.gaminidsystems.pagination

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Study::class], version = 1,exportSchema = false)
abstract class StudyRoomDatabase : RoomDatabase() {

    abstract fun studyDao(): StudyDao

    companion object {
        @Volatile
        private var INSTANCE: StudyRoomDatabase? = null

        fun getDatabase(context: Context): StudyRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyRoomDatabase::class.java,
                    "study_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}