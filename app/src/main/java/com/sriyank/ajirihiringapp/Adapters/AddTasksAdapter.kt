package com.sriyank.ajirihiringapp.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.ajirihiringapp.MainActivity
import com.sriyank.ajirihiringapp.PostTaskActivityDetails
import com.sriyank.ajirihiringapp.R
import com.sriyank.ajirihiringapp.model.TaskItems
import com.sriyank.ajirihiringapp.utils.AddTasksViewHolder
import com.sriyank.ajirihiringapp.utils.taskItems

class AddTasksAdapter: RecyclerView.Adapter<AddTasksViewHolder>(){

    private lateinit var mlistener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTasksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_task_layoutitem, parent, false)
        return AddTasksViewHolder(view, mlistener)
//        return AddTasksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddTasksViewHolder, position: Int) {
        val individualtaskItem = taskItems[position]
        holder.bind(individualtaskItem)
    }

    override fun getItemCount(): Int {
        return taskItems.size
    }

}