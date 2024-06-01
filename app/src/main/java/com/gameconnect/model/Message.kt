package com.gameconnect.model

import com.google.firebase.Timestamp

data class Message(
    val id: String = "",
    val userId : String = "",
    val date : Timestamp = Timestamp.now(),
    val content : String = ""
)