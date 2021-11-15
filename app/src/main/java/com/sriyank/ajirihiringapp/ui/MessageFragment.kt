package com.sriyank.ajirihiringapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.sriyank.ajirihiringapp.Adapters.ChatUserAdapter
import com.sriyank.ajirihiringapp.MainActivity
import com.sriyank.ajirihiringapp.R
import com.sriyank.ajirihiringapp.model.Users
import com.sriyank.ajirihiringapp.model.toDatabase

class MessageFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<Users>
    private lateinit var adapter: ChatUserAdapter
    private lateinit var mDbref: DatabaseReference
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        userList = ArrayList()
        adapter = ChatUserAdapter(requireContext(), userList)

        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerView.adapter = adapter

        mDbref = FirebaseDatabase.getInstance().getReference()

            mDbref.child("user").child(userId).child("profile").addValueEventListener(object: ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (postsnapshot in snapshot.children){
                        val currentUser = postsnapshot.getValue(Users::class.java)

                        if (firebaseAuth.currentUser?.uid != userId){
                            userList.add(currentUser!!)
                        }

                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}