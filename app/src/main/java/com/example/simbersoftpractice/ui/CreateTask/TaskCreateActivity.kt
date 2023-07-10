package com.example.simbersoftpractice.ui.CreateTask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.simbersoftpractice.R
import com.example.simbersoftpractice.model.Task
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

class TaskCreateActivity : AppCompatActivity() {

    private lateinit var btnCreate: Button
    private lateinit var btnBack: ImageButton
    private lateinit var datePicker: DatePicker
    private lateinit var nameTask: EditText
    private lateinit var description: EditText
    private lateinit var timeStart: TimePicker
    private lateinit var timeEnd: TimePicker
    private lateinit var viewModel: TaskCreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_create)

        btnCreate = findViewById(R.id.btn_createTask)
        btnBack = findViewById(R.id.btn_back)
        datePicker = findViewById(R.id.dp_start)
        nameTask = findViewById(R.id.ed_title)
        description = findViewById(R.id.et_description)
        timeStart = findViewById(R.id.tp_start)
        timeEnd = findViewById(R.id.tp_end)

        val deviceTimeZone = ZoneId.systemDefault()
        viewModel = ViewModelProvider(this)[TaskCreateViewModel::class.java]


        btnCreate.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth

            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

            val startHour = timeStart.hour
            val startMinute = timeStart.minute
            val endHour = timeEnd.hour
            val endMinute = timeEnd.minute

            val startDateTime =
                selectedDate.atTime(startHour, startMinute).atZone(deviceTimeZone).toLocalDateTime()
                    .toEpochSecond(
                        ZoneOffset.UTC
                    )
            val endDateTime =
                selectedDate.atTime(endHour, endMinute).atZone(deviceTimeZone).toLocalDateTime()
                    .toEpochSecond(ZoneOffset.UTC)

            if (nameTask.text.isEmpty() || description.text.isEmpty()) {
                Toast.makeText(this, "Fill in the name and description fields", Toast.LENGTH_LONG)
                    .show()
            } else {
                viewModel.createTask(
                    Task(
                        id= 0,
                        name = nameTask.text.toString(),
                        description = description.text.toString(),
                        date_start = startDateTime,
                        date_finish = endDateTime
                    )
                )
                Toast.makeText(this, "Task created", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}