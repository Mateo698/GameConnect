package com.gameconnect.repository

import android.net.Uri
import android.util.Log
import com.gameconnect.domain.model.User
import com.gameconnect.domain.model.UserCard
import com.gameconnect.model.Chat
import com.gameconnect.services.FileServices
import com.gameconnect.services.UserServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

interface UserRepository {

    suspend fun loadUser() : User?
    suspend fun updateProfileImage(uri: Uri, filename: String)
    suspend fun observeAllUsers() : List<UserCard>
    suspend fun loadSpecificUser(id: String) : User?
    suspend fun observeUserChats(userId: String): List<Chat>
    suspend fun getUsers(toList: List<String>): List<User>
    suspend fun loadChatInfo(id: String): Chat
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

    override suspend fun observeAllUsers() : List<UserCard> {
        val users = userServices.observeAllUsers()
        return users.filter { it.id != Firebase.auth.uid }
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
}