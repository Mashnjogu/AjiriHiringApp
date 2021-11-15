package com.sriyank.ajirihiringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sriyank.ajirihiringapp.Adapters.PostTaskVPAdapter
import com.sriyank.ajirihiringapp.Adapters.TaskVPCharges

class PostTaskFragmentBudget : Fragment() {

    private lateinit var pricetabs: TabLayout
    private lateinit var priceViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_task_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        priceViewPager = view.findViewById(R.id.priceViewPager)
        pricetabs = view.findViewById(R.id.pricetabs)

        val pageAdapter = TaskVPCharges(requireActivity().supportFragmentManager, lifecycle)
        priceViewPager.adapter = pageAdapter

        TabLayoutMediator(pricetabs, priceViewPager){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Per Hour"
                }
                1 -> {
                    tab.text = "Whole Price"
                }
            }
        }.attach()
    }
}