package com.example.bitfitpart1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SleepRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao
}
