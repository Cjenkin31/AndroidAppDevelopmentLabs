package com.example.bitfitpart1

import androidx.lifecycle.LiveData
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

    @Query("SELECT AVG(hours) FROM SleepRecord")
    fun getAverageSleepHours(): LiveData<Double>

    @Query("SELECT COUNT(id) FROM SleepRecord")
    fun getSleepRecordCount(): LiveData<Int>

    @Query("DELETE FROM SleepRecord")
    fun deleteAll()
}
