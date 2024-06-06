package com.gameconnect.domain.model

import com.gameconnect.model.Game

data class User(
    var id: String = "",
    var username: String = "",
    var genres: List<String> = listOf(),
    var platforms: List<String> = listOf(),
    var games: List<String> = listOf(),
    var gamertags: List<String> = listOf(),
    var email: String = "",
    var password: String = "",
    var time: String = "",
    var biography: String = "",
    var profilePic: String? = null
)