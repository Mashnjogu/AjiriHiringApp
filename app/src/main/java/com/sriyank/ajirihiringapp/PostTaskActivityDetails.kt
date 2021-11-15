package com.sriyank.ajirihiringapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sriyank.ajirihiringapp.Adapters.PostTaskVPAdapter

class PostTaskActivityDetails : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_task_details)

        tabLayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.viewpager)

        viewPager.isUserInputEnabled = false

        val pageAdapter = PostTaskVPAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pageAdapter


        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Task Details"
                }
                1 -> {
                    tab.text = "Date and Time"
                }
                2 -> {
                    tab.text = "Budget"
                }
            }
        }.attach()
        //change must-haves to professional preferences
    }
}

//class SwpipeLockableViewPager(context: Context, attr: AttributeSet): ViewPager(context, attr){
//        private var swapEnabled = false
//    override fun onTouchEvent(ev: MotionEvent?): Boolean {
//        return when(swapEnabled){
//            true -> super.onTouchEvent(ev)
//            false -> false
//        }
//    }
//
//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        return when(swapEnabled){
//            true -> super.onInterceptTouchEvent(ev)
//            false -> false
//        }
//    }
//}