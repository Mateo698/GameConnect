package com.gameconnect.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gameconnect.databinding.ChatItemBinding

class ChatViewHolder(root: View): ViewHolder(root){
    private val binding = ChatItemBinding.bind(root)
    val username = binding.usernameTextView
    val lastMessage = binding.lastMssg
    val deleteButton = binding.deleteButton
    val userImg = binding.userImg
    val chatBtn = binding.chatButton
}