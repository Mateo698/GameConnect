package com.gameconnect.repository

import android.net.Uri
import android.util.Log
import com.gameconnect.domain.model.User
import com.gameconnect.domain.model.UserCard
import com.gameconnect.model.Chat
import com.gameconnect.model.Message
import com.gameconnect.services.FileServices
import com.gameconnect.services.UserServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

interface UserRepository {

    suspend fun loadUser() : User?
    suspend fun updateProfileImage(uri: Uri, filename: String)
    fun observeAllUsers(onUsersChanged : (List<UserCard>) -> Unit)
    suspend fun loadSpecificUser(id: String) : User?
    suspend fun observeUserChats(userId: String): List<Chat>
    suspend fun loadChatInfo(id: String): Chat
    suspend fun getUsers(toList: List<String>): List<User>
    suspend fun createMatch(matchId: UserCard)
    suspend fun sendMessage(message: Message, chatId: String)
    suspend fun observeChatMessages(id: String, function: (Message) -> Unit)
}

class UserRepositoryImpl(
    val userServices: UserServices = UserServices(),
    val fileServices: FileServices = FileServices()
) : UserRepository {
    override suspend fun loadUser(): User? {
        val document = userServices.loadUser(Firebase.auth.uid!!)
        val user = document.toObject(User::class.java)
        user?.profilePic?.let {
            user.profilePic = fileServices.downloadImage(it).toString()
        }
        return user
    }
    override suspend fun updateProfileImage(uri: Uri, filename: String) {
        fileServices.uploadFile(uri, filename)
        userServices.updateProfileImage(filename)
    }

    override fun observeAllUsers(onUsersChanged: (List<UserCard>) -> Unit) {
        userServices.observeAllUsers(onUsersChanged)
    }

    override suspend fun loadSpecificUser(id: String) : User? {
        val document = userServices.loadUser(id)
        val user = document.toObject(User::class.java)
        user?.profilePic?.let {
            user.profilePic = fileServices.downloadImage(it).toString()
        }
        return user
    }

    override suspend fun observeUserChats(userId: String): List<Chat> {
        return userServices.observeUserChats(userId)
    }

    override suspend fun getUsers(toList: List<String>): List<User> {
        return userServices.getUsers(toList)
    }

    override suspend fun loadChatInfo(id: String): Chat {
        return userServices.loadChatInfo(id)
    }
    override suspend fun createMatch(match: UserCard) {
        userServices.createMatch(match)
    }

    override suspend fun sendMessage(message: Message, chatId: String) {
        userServices.sendMessage(message, chatId)
    }

    override suspend fun observeChatMessages(id: String, function: (Message) -> Unit) {
        userServices.observeChatMessages({
            val message = it.toObject(Message::class.java)
            function(message)
        }, id)
    }
}