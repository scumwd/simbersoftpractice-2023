package com.example.simbersoftpractice.data.repository

import android.content.Context
import com.example.simbersoftpractice.data.TaskDatabase
import com.example.simbersoftpractice.data.readTasksFromJson
import com.example.simbersoftpractice.model.Task
import com.example.simbersoftpractice.model.TaskEntity

class TaskRepository(private val context: Context) {
    private val taskDao = TaskDatabase.getDatabase(context).taskDao()

    suspend fun insertTasksFromJson() {
        val tasks = readTasksFromJson(context)
        val taskEntities = tasks.map { it.toTaskEntity() }
        taskDao.insertTasks(taskEntities)
    }

    suspend fun getAllTasks(): List<Task> {
        val taskEntities = taskDao.getAllTasks()
        return taskEntities.map { it.toTask() }
    }

    suspend fun getTasksForDate(
        startDateTime: Long,
        endDateTime: Long
    ): List<Task> {
        val taskEntities = taskDao.getAllTasksByDateTime(startDateTime, endDateTime)
        return taskEntities.map { it.toTask() }
    }

    private fun TaskEntity.toTask(): Task {
        return Task(
            id = id.toInt(),
            date_start = startDate,
            date_finish = endDate,
            name = name,
            description = description
        )
    }

    private fun Task.toTaskEntity(): TaskEntity {
        return TaskEntity(
            id = id.toLong(),
            name = name,
            description = description,
            startDate = date_start,
            endDate = date_finish
        )
    }
}
