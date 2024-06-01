package com.gameconnect.model

data class ChatItem(
    val userImg: String = "",
    val username: String = "",
    val lastMessage: Message = Message(),
    val chatId: String = "",
)