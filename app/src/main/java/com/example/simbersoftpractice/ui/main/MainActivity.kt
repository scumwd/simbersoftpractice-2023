package com.example.simbersoftpractice.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simbersoftpractice.R
import com.example.simbersoftpractice.model.Task
import com.example.simbersoftpractice.model.TimeSlot
import com.example.simbersoftpractice.ui.CreateTask.TaskCreateActivity
import com.example.simbersoftpractice.ui.TaskDetails.TaskDetailsActivity
import com.example.simbersoftpractice.ui.main.adapter.OnTaskClickListener
import com.example.simbersoftpractice.ui.main.adapter.TasksSlotAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset


class MainActivity : AppCompatActivity(), OnTaskClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var rvSlot: RecyclerView
    private lateinit var adapter: TasksSlotAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidThreeTen.init(this)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        rvSlot = findViewById(R.id.rv_tasks)
        val btnCreate: Button = findViewById(R.id.ibtn_createTask)
        rvSlot.layoutManager = GridLayoutManager(this, 2)

        adapter = TasksSlotAdapter(emptyList(), this)
        rvSlot.adapter = adapter

        viewModel.insertTasksFromJson()

        showTask()

        btnCreate.setOnClickListener {
            val intent = Intent(this, TaskCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentDateTime = LocalDateTime.now()
        val currentDate = LocalDate.now()
        val startDateTime = currentDateTime.withHour(0).withMinute(0).withSecond(0)
            .toEpochSecond(ZoneOffset.ofTotalSeconds(ZoneId.systemDefault().rules.getOffset(currentDateTime).totalSeconds))

        val endDateTime = currentDateTime.withHour(23).withMinute(59).withSecond(59)
            .toEpochSecond(ZoneOffset.ofTotalSeconds(ZoneId.systemDefault().rules.getOffset(currentDateTime).totalSeconds))

        lifecycleScope.launch {
            val tasks = viewModel.getAllTasksByDate(startDateTime, endDateTime)
            val timeSlots = createTimeSlots(tasks, currentDate)
            adapter.setTimeSlots(timeSlots)
        }
    }

    private fun showTask() {
        val calendarView = findViewById<CalendarView>(R.id.cv_pickDate)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            val startDateTime = selectedDateTime.withHour(0).withMinute(0).withSecond(0)
                .toEpochSecond(ZoneOffset.ofTotalSeconds(ZoneId.systemDefault().rules.getOffset(selectedDateTime).totalSeconds))
            val endDateTime = selectedDateTime.withHour(23).withMinute(59).withSecond(59)
                .toEpochSecond(ZoneOffset.ofTotalSeconds(ZoneId.systemDefault().rules.getOffset(selectedDateTime).totalSeconds))

            lifecycleScope.launch {
                val tasks = viewModel.getAllTasksByDate(startDateTime, endDateTime)
                val timeSlots = createTimeSlots(tasks, selectedDate)
                adapter.setTimeSlots(timeSlots)
            }
        }
    }


    private fun createTimeSlots(tasks: List<Task>, selectedDate : LocalDate): List<TimeSlot> {
        val timeSlots = mutableListOf<TimeSlot>()
        val timeZone = ZoneId.systemDefault()

        for (hour in 0..23) {
            val startTime = LocalDateTime.of(selectedDate, LocalTime.of(hour, 0))
            val endTime = startTime.plusHours(1)
            val tasksInTimeSlot = tasks.filter { task ->
                val taskTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(task.date_start), timeZone)
                taskTime >= startTime && taskTime < endTime
            }
            val timeSlot = TimeSlot("${hour.toString().padStart(2, '0')}:00", tasksInTimeSlot)
            timeSlots.add(timeSlot)
        }
        return timeSlots
    }

    override fun onTaskClick(task: Task) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

}