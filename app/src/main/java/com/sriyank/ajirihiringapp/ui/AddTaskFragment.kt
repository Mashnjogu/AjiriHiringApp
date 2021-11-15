package com.sriyank.ajirihiringapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.ajirihiringapp.Adapters.AddTasksAdapter
import com.sriyank.ajirihiringapp.PostTaskActivityDetails
import com.sriyank.ajirihiringapp.R

class AddTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addTaskRecView = view.findViewById<RecyclerView>(R.id.add_task_rv)
        val adapter = AddTasksAdapter()

        addTaskRecView.layoutManager = GridLayoutManager(activity, 2)
        addTaskRecView.adapter = adapter
        adapter.setOnItemClickListener(object: AddTasksAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                requireActivity().run{
                    startActivity(Intent(this, PostTaskActivityDetails::class.java))
                }
            }

        })
    }
}