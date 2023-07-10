package com.example.simbersoftpractice

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.example.simbersoftpractice.model.Task
import com.example.simbersoftpractice.ui.CreateTask.TaskCreateViewModel
import com.example.simbersoftpractice.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class UnitTests {
    @Test
    fun createTask() {
        val taskName = "Task 1"
        val taskDescription = "Task description"
        val taskTimeStart: Long = 1688904000
        val taskTimeEnd: Long = 1688909000
        val task = Task(0, taskTimeStart, taskTimeEnd, taskName, taskDescription)
        val application = ApplicationProvider.getApplicationContext<Application>()
        val createViewModel = TaskCreateViewModel(application)
        val mainViewModel = MainViewModel(application)

        createViewModel.createTask(task)

        runBlocking  {
            val tasks = mainViewModel.getAllTasks()
            assertTrue(tasks.any { it.name == taskName && it.description == taskDescription })
        }
    }
}