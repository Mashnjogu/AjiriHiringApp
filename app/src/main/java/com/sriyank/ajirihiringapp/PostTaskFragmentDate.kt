package com.sriyank.ajirihiringapp

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sriyank.ajirihiringapp.model.TaskTimeDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class PostTaskFragmentDate : Fragment() {

    private lateinit var date_btn: Button
    private lateinit var time_btn: Button
    private lateinit var txt_date: TextView
    private lateinit var txt_time: TextView
    private lateinit var savetime: Button

    //error could occur here make it lateinit var
    var selectedTime: String = ""
    var selectedDate: String = ""

    var tskTime = TaskTimeDate(selectedTime, selectedDate)

    private val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser!!.uid
    private val database = FirebaseDatabase.getInstance()
    val databaseRef = database.getReference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_task_date, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        date_btn = view.findViewById(R.id.date_btn)
        time_btn = view.findViewById(R.id.time_btn)
        txt_time = view.findViewById(R.id.tx_time)
        txt_date = view.findViewById(R.id.txt_date)
        savetime = view.findViewById(R.id.save_time)

        date_btn.setOnClickListener {
            pickDate()
        }

        time_btn.setOnClickListener {
            pickTime()
        }

        savetime.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                addDateTimeToDb()
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun pickTime() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val timePicker = MaterialTimePicker.Builder().setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select Task Time")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                 selectedTime = SimpleDateFormat("hh:mm aa").format(calendar.time)
                txt_time.text = "The Selected time is: $selectedTime"
            }
            TimePickerDialog(requireContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), false).show()
        }
        timePicker.show(requireActivity().supportFragmentManager, "TimePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun pickDate() {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Task date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("EAT"))
            calendar.time = Date(it)
            selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)} - " +
            "${calendar.get(Calendar.MONTH)} - ${calendar.get(Calendar.YEAR)}"
            txt_date.text = "Selected Date: $selectedDate"
        }

        datePicker.show(requireActivity().supportFragmentManager, "DatePicker")
    }

    private fun addDateTimeToDb(){
        databaseRef.child("user").child(uid).child("taskInfo")
            .child("taskDateTime").setValue(
                //error could occur here return to TaskTimeDate(selectedTime, selectedDate)
                tskTime
            )
    }

}