package com.example.simbersoftpractice.ui.CreateTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbersoftpractice.data.repository.TaskRepository
import com.example.simbersoftpractice.model.Task
import kotlinx.coroutines.launch

class TaskCreateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository = TaskRepository(application)

    fun createTask (task:Task){
        viewModelScope.launch {
            repository.createTask(task)
        }
    }
}