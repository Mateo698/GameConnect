package com.gameconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.adapter.ChatAdapter
import com.gameconnect.adapter.ChatMessagesAdapter
import com.gameconnect.databinding.ActivityChatBinding
import com.gameconnect.model.Message
import com.gameconnect.viewmodel.ChatViewModel
import com.gameconnect.viewmodel.RegisterViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth

class ChatActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityChatBinding.inflate(layoutInflater)
    }

    private val viewModel: ChatViewModel by viewModels()

    val chatAdapter = ChatMessagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val chatId = intent.getStringExtra("chatId")
        viewModel.getChatInfo(chatId!!)

        viewModel.otherUser.observe(this){
            binding.usernameChatLabel.text = it?.username
        }

        binding.backButton.setOnClickListener {
            super.finish()
        }

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            intent.putExtra("id", viewModel.otherUser.value?.id)

            startActivity(intent)
        }

        binding.sendButton.setOnClickListener {
            val message = Message(
                "",
                Firebase.auth.uid!!,
                Timestamp(System.currentTimeMillis() / 1000, 0),
                binding.messageTextEdit.text.toString()
            )
            viewModel.sendMessage(message)
            binding.messageTextEdit.text.clear()
        }

        binding.messagesRV.setHasFixedSize(true)
        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messagesRV.layoutManager = manager
        binding.messagesRV.adapter = chatAdapter

        viewModel.chat.observe(this){
            chatAdapter.messages = it?.messages ?: arrayListOf()
            chatAdapter.notifyDataSetChanged()
            if(chatAdapter.itemCount>0) {
                binding.messagesRV.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}