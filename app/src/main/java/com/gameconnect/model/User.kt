package com.gameconnect.model

data class User(
    val username: String,
    val genres: List<String>,
    val platforms: List<String>,
    val games: List<String>,
    val gamertags: List<String>,
    val email: String,
    val password: String,
    val time: String
)