package com.example.simbersoftpractice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simbersoftpractice.model.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(taskEntities: List<TaskEntity>)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE startDate >= :startDateTime AND startDate <= :endDateTime")
    suspend fun getAllTasksByDateTime(startDateTime: Long, endDateTime: Long): List<TaskEntity>
}