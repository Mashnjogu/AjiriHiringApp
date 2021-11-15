package com.sriyank.ajirihiringapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sriyank.ajirihiringapp.taskCharges.PerHour
import com.sriyank.ajirihiringapp.taskCharges.WholePrice

class TaskVPCharges(fm :FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> {
               PerHour()
           }
           1 -> {
               WholePrice()
           }
           else -> {
               Fragment()
           }
       }
    }

}