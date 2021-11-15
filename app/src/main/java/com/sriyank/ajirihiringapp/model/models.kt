package com.sriyank.ajirihiringapp.model

import android.net.Uri
import android.widget.ImageView
import com.google.android.gms.tasks.Task
import com.google.firebase.database.IgnoreExtraProperties
import com.sriyank.ajirihiringapp.R

data class TaskItems(
    val taskImage: Int,
    val taskName: String
)

//class Users{
//    var name: String? = null
//    var email: String? = null
//    var uid: String? = null
//    var pImage: Int? = null
//
//    constructor(){}
//
//    constructor(name: String?, email:String?, uid: String?, pImage: Int?){
//        this.name = name
//        this.email = email
//        this.uid = uid
//        this.pImage = pImage
//    }
//}

data class Users(
    var name: String,
    var email: String,
    val uid: String,
    var image: Int? = null
)

data class toDatabase(
    var name: String,
    var email: String,
    val uid: String,
    var image: String?
)


@IgnoreExtraProperties
data class TaskLocation(
    var latitude: Double? = null,
    var longitude: Double? = null
)

data class TaskDetails(
    var taskTitle: String,
    var taskDescription: String,
    var mustHaves: String?,
    var taskImage: String?
)

data class TaskTimeDate(
    var tastTime: String,
    var taskDate: String
)

data class TaskBudget(
    var wholePrice: String?,
    var perHour: String?,
    var numHours: String?,
    var toalPHour: String
)


