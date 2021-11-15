package com.sriyank.ajirihiringapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.sriyank.ajirihiringapp.model.toDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.net.URL

class UserNamePic: Fragment(){

    private lateinit var profileImage: ImageView
    private lateinit var chooseImgBtn: ImageView
    private lateinit var userName: TextInputLayout
    private lateinit var saveInfo: Button


    private var name = ""


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    val databaseRef = database.getReference()

    val uid = firebaseAuth.currentUser
   private val storageReference = FirebaseStorage.getInstance()
        .getReference("profilePhoto/$uid")

    private val imageUri: Task<Uri> = storageReference.downloadUrl.addOnCompleteListener{
        if (it.isSuccessful){
             it.result
        }
    }

    val kk = storageReference.downloadUrl.addOnSuccessListener {
        return@addOnSuccessListener
        Log.d("UserActivity", "The link is: $it")
    }





//    val hg = storageReference.downloadUrl.addOnCompleteListener{
//        if (it.isSuccessful){
//             it.result
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.username, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.ProfileImage)
        chooseImgBtn = view.findViewById(R.id.ChooseImg_btn)
        userName = view.findViewById(R.id.Username)
        saveInfo = view.findViewById(R.id.saveInfo_btn)

        chooseImgBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                selectImage()
            }

        }
        saveInfo.setOnClickListener {
            saveInfoToDatabase()
            findNavController().navigate(R.id.action_userNamePic_to_signIn)
        }

    }

    private fun saveInfoToDatabase() {
        name = userName.editText!!.text.toString().trim()
        if (name.isEmpty()){
            userName.editText?.error = "UserName cannot be empty"
        }else{

            val uid = firebaseAuth.currentUser!!.uid
            val email = firebaseAuth.currentUser!!.email

            //from here
            val file = Uri.fromFile(File("gs://ajiri-hiring.appspot.com/profilePhoto/$uid"))
            val imageRef = storageReference.putFile(file)

            val mcv = imageRef.continueWithTask {task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw  it
                    }
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val imageUri = task.result!!
                    databaseRef.child("user/$uid/profile").child("image")
                        .setValue(imageUri.toString())
                }
            }

            //end
            if (email != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    userInfoToDatabase(email, name, uid, imageUri.toString())
                }
            }

        }
    }

    private suspend fun userInfoToDatabase(email: String, name: String, uid: String, image: String?){

        databaseRef.child("user").child(uid).child("profile")
            .setValue(toDatabase(name, email, uid, image)).await()
    }
    private fun selectImage() {
        getImage.launch("image/")
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        storageReference.putFile(uri).addOnSuccessListener {
            Glide.with(requireContext())
                .load(uri)
                .into(profileImage)
//            profileImage.setImageURI(uri)
            Toast.makeText(requireContext(), "Successfull", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Retry", Toast.LENGTH_SHORT).show()
        }

    }

    fun imageUri(){
        //link to the particular file
        val file = Uri.fromFile(File("gs://ajiri-hiring.appspot.com/profilePhoto/$uid"))
        val imageRef = storageReference.putFile(file)

        val m = imageRef.continueWithTask {task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw  it
                }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
           if (task.isSuccessful) {
               val imageUri = task.result
               databaseRef.child("user/$uid/profile").child("image")
                   .setValue(imageUri.toString())
           }
       }
    }
}