package com.gameconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gameconnect.databinding.ActivityChatBinding
import com.gameconnect.viewmodel.ChatViewModel
import com.gameconnect.viewmodel.RegisterViewModel

class ChatActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityChatBinding.inflate(layoutInflater)
    }

    private val viewModel: ChatViewModel by viewModels()

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



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}