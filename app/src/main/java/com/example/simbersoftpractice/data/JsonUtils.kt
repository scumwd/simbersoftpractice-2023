package com.example.simbersoftpractice.data

import android.content.Context
import com.example.simbersoftpractice.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun readTasksFromJson(context: Context): List<Task> {
    val tasks: List<Task>
    try {
        val inputStream = context.assets.open("tasks.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val taskType = object : TypeToken<List<Task>>() {}.type
        tasks = Gson().fromJson(json, taskType)
    } catch (exception: IOException) {
        exception.printStackTrace()
        return emptyList()
    }
    return tasks
}