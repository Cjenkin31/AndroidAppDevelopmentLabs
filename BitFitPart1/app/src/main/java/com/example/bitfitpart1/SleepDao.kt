package com.example.bitfitpart1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepDao {
    @Insert(entity = SleepRecord::class)
    fun insertAll(sleepRecord: SleepRecord)

    @Query("SELECT * FROM SleepRecord")
    fun getAllSleepRecords(): List<SleepRecord>
}
