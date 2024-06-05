package com.gameconnect.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gameconnect.R
import com.gameconnect.databinding.IncomingMessageBinding
import com.gameconnect.databinding.OutgoingMessageBinding
import com.gameconnect.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ChatMessagesAdapter(
    var messages: ArrayList<Message> = arrayListOf()
) : Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val userId = Firebase.auth.uid
        if(messages[position].userId == userId){
            return 1
        }else{
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val root = LayoutInflater.from(parent.context).inflate(R.layout.incoming_message, parent, false)
            return MessageViewHolder(root)
        } else {
            val root = LayoutInflater.from(parent.context).inflate(R.layout.outgoing_message, parent, false)
            return OtherMessageViewHolder(root)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is MessageViewHolder -> {
                holder.messageTV.text = messages[position].content
            }
            is OtherMessageViewHolder -> {
                holder.messageTV.text = messages[position].content
            }
        }
    }


}

class MessageViewHolder(root: View) : ViewHolder(root) {
    private val binding = IncomingMessageBinding.bind(root)
    val messageTV = binding.textView14
}

class OtherMessageViewHolder(root: View) : ViewHolder(root) {
    private val binding = OutgoingMessageBinding.bind(root)
    val messageTV = binding.textView12
}
