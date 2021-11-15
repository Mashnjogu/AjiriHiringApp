package com.sriyank.ajirihiringapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.ajirihiringapp.Adapters.AddTasksAdapter
import com.sriyank.ajirihiringapp.R
import com.sriyank.ajirihiringapp.model.TaskItems

class AddTasksViewHolder(view: View, listener: AddTasksAdapter.onItemClickListener): RecyclerView.ViewHolder(view){

     val addTaskImg = view.findViewById<ImageView>(R.id.add_task_img)
     val addTaskTxt = view.findViewById<TextView>(R.id.add_task_txt)

    fun bind(taskItems: TaskItems){
        val task_item_img = taskItems.taskImage
        val task_item_desc = taskItems.taskName

        addTaskImg.setImageResource(task_item_img)
        addTaskTxt.text = task_item_desc
    }

//    companion object{
//        fun create(parent: ViewGroup): AddTasksViewHolder{
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.add_task_layoutitem, parent, false)
//
//            return AddTasksViewHolder(view,)
//        }
//    }

    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val userName = itemView.findViewById<TextView>(R.id.recipientName)
    val userImage = itemView.findViewById<ImageView>(R.id.reciientPImage)
}

