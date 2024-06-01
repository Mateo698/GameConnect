package com.gameconnect.model

data class Chat (
    val id: String = "",
    val userOne: String = "",
    val userTwo: String = "",
    val messages: List<Message> = listOf()
)