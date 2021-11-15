package com.sriyank.ajirihiringapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.ajirihiringapp.R
import com.sriyank.ajirihiringapp.model.Users
import com.sriyank.ajirihiringapp.ui.MessageFragment
import com.sriyank.ajirihiringapp.utils.UserViewHolder

class ChatUserAdapter(val context: Context, val userList: ArrayList<Users>):
    RecyclerView.Adapter<UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.chat_user_view_item, parent,
            false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userName.text = currentUser.name
        currentUser.image?.let { holder.userImage.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}
