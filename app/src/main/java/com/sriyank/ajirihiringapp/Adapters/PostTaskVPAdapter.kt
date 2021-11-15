package com.sriyank.ajirihiringapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sriyank.ajirihiringapp.PostTaskFragment1
import com.sriyank.ajirihiringapp.PostTaskFragmentBudget
import com.sriyank.ajirihiringapp.PostTaskFragmentDate

class PostTaskVPAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                PostTaskFragment1()
            }
            1 -> {
                PostTaskFragmentDate()
            }
            2 -> {
                PostTaskFragmentBudget()
            }
            else -> {
                Fragment()
            }
        }
    }

}