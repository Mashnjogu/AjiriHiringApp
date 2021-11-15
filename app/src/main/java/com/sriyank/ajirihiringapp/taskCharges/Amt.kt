package com.sriyank.ajirihiringapp.taskCharges

import com.sriyank.ajirihiringapp.MapsActivity
import com.sriyank.ajirihiringapp.PostTaskFragment1
import com.sriyank.ajirihiringapp.PostTaskFragmentDate
import com.sriyank.ajirihiringapp.model.TaskBudget

class Amt {
    var wholePrice = WholePrice().amount.toString()
    var perHour = PerHour().shillings.toString()
    var numHours = PerHour().hours.toString()
    var totalPHours = PerHour().total.toString()
    var taskLocation = MapsActivity().locationLogging
    var taskDateTime = PostTaskFragmentDate().tskTime
    var tskBudget = TaskBudget(wholePrice, perHour, numHours, totalPHours)
    var tskCat = PostTaskFragment1()
}