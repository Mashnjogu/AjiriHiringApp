package com.sriyank.ajirihiringapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.sriyank.ajirihiringapp.model.TaskDetails
import com.sriyank.ajirihiringapp.taskCharges.PerHour
import com.sriyank.ajirihiringapp.taskCharges.WholePrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "PostActivity"
class PostTaskFragment1 : Fragment() {

    private lateinit var taskLocation: TextView
    private lateinit var taskImg: ImageButton
    private lateinit var taskTitle: EditText
    private lateinit var taskDescription: EditText
    private lateinit var taskAddition: EditText
    private lateinit var taskDetailSave: Button
    //incase of error below at line 96 remove this line
    var selectedItem = ""
    private val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser!!.uid
    private val storageReference = FirebaseStorage.getInstance()
        .getReference("taskImages/$uid")
    private val database = FirebaseDatabase.getInstance()
    val databaseRef = database.getReference()

        val taskKey = databaseRef.child("user").child(uid).child("taskInfo")
            .push().key
    private val imageUri: Task<Uri> = storageReference.downloadUrl.addOnCompleteListener{
        if (it.isSuccessful){
            it.result
        }
    }

    //accessing variables from other classes
    var wholePrice = WholePrice().amount.toString()
    var perHour = PerHour().shillings.toString()
    var numHours = PerHour().hours.toString()
    var totalPHours = PerHour().total.toString()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_post_task1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskLocation = view.findViewById(R.id.taskLocation)
        taskTitle = view.findViewById(R.id.taskTitle)
        taskDescription = view.findViewById(R.id.taskDescription)
        taskAddition = view.findViewById(R.id.task_addition)
        taskDetailSave = view.findViewById(R.id.task_detail_save)

        taskLocation.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }

        taskImg = view.findViewById(R.id.imageButton2)
        taskImg.setOnClickListener {
            selectImage()
        }

        val taskCat = resources.getStringArray(R.array.task_category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.task_list_item_cat, taskCat)
        val autoCompleteTextView= view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTxt)
        autoCompleteTextView.setAdapter(arrayAdapter)

        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            //error could occur here change back to val selectedItem
             selectedItem = parent.getItemAtPosition(position).toString()
            databaseRef.child("user").child(uid).child("taskInfo")
                .child("taskCategory").setValue(selectedItem)
            Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
        }

        taskDetailSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                validateData()
            }
            Toast.makeText(requireContext(), "Details Saved",Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        getTaskImage.launch("image/*")
    }

    private val getTaskImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        storageReference.putFile(uri).addOnSuccessListener {
            Glide.with(requireContext())
                .load(uri)
                .into(taskImg)
//            profileImage.setImageURI(uri)
            Toast.makeText(requireContext(), "Successfull", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Retry", Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateData(){
        var tskTitle = taskTitle.text.toString()
        var tskDesc = taskDescription.text.toString()
        var tskAdd = taskAddition.text.toString()

        if (tskDesc.isEmpty()|| tskTitle.isEmpty()){
            taskTitle.error = "Task Title and Task Descripton cannot be empty"
        }else{
                databaseRef.child("user").child(uid).child("taskInfo")
                    .child("taskDetails").setValue(
                        TaskDetails(tskTitle, tskDesc, tskAdd, imageUri.toString()))
        }
    }
}