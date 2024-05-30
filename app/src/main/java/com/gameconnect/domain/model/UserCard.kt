package com.gameconnect.domain.model

data class UserCard (
    var id: String = "",
    var username: String = "",
    var platform: String = "",
    var games: List<String> = listOf(),
    var profilePic: String? = null,
    var biography: String = ""
)