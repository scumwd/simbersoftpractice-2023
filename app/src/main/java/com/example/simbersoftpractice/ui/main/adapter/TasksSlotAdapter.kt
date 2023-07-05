package com.example.simbersoftpractice.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simbersoftpractice.R
import com.example.simbersoftpractice.model.TimeSlot

class TasksSlotAdapter(private var timeSlots: List<TimeSlot>, private val onTaskClickListener: OnTaskClickListener) : RecyclerView.Adapter<TasksSlotAdapter.TimeSlotViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.time_slot_item, parent, false)
        return TimeSlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.timeTextView.text = timeSlot.time

        holder.tasksRecyclerView.layoutManager = LinearLayoutManager (holder.itemView.context)
        val adapter = TasksAdapter(timeSlot.tasks, onTaskClickListener)
        holder.tasksRecyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return timeSlots.size
    }

    fun setTimeSlots(timeSlots: List<TimeSlot>){
        this.timeSlots = timeSlots
        notifyDataSetChanged()
    }

    inner class TimeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
        val tasksRecyclerView: RecyclerView = itemView.findViewById(R.id.rv_tasks)
    }

}
