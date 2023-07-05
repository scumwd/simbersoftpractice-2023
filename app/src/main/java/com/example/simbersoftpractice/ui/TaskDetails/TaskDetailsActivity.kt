package com.example.simbersoftpractice.ui.TaskDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.simbersoftpractice.R
import com.example.simbersoftpractice.model.Task
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class TaskDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val task = intent?.getSerializableExtra("task") as? Task
        val name: TextView = findViewById(R.id.tv_title)
        val timeStat: TextView = findViewById(R.id.tv_startDateTime)
        val timeEnd: TextView = findViewById(R.id.tv_finishDateTime)
        val description: TextView = findViewById(R.id.tv_description)
        val btnBack: ImageButton = findViewById(R.id.btn_back)

        if (task != null) {
            name.text = task.name
            description.text = task.description
            timeStat.text = formatTime(task.date_start)
            timeEnd.text = formatTime(task.date_finish)

        }

        btnBack.setOnClickListener{
            finish()
        }

    }

    private fun formatTime(time: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault())
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}