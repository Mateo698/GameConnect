package com.gameconnect.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gameconnect.R
import com.gameconnect.model.Chat
import com.gameconnect.model.ChatItem
import com.gameconnect.viewholder.ChatViewHolder
import com.gameconnect.viewholder.GameViewHolder
import com.squareup.picasso.Picasso

class ChatAdapter: Adapter<ChatViewHolder>() {

    private val chats: ArrayList<ChatItem> = arrayListOf()

    fun setChats(chats: List<ChatItem>){
        this.chats.clear()
        this.chats.addAll(chats)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(root)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.username.text = chats[position].username
        holder.lastMessage.text = chats[position].lastMessage.content
        Picasso.get().load(chats[position].userImg).into(holder.userImg)
        holder.chatBtn.setOnClickListener {
            /**
            Intent(holder.itemView.context, Chat::class.java).apply {
                putExtra("chatId", chats[position].chatId)
                holder.itemView.context.startActivity(this)
            }**/
            Log.e("ChatAdapter", "Chat button clicked with id ${chats[position].chatId}")
        }

    }

}