package com.gameconnect.repository

import android.net.Uri
import com.gameconnect.domain.model.User
import com.gameconnect.domain.model.UserCard
import com.gameconnect.services.FileServices
import com.gameconnect.services.UserServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

interface UserRepository {

    suspend fun loadUser() : User?
    suspend fun updateProfileImage(uri: Uri, filename: String)
    fun observeAllUsers(onUsersChanged : (List<UserCard>) -> Unit)
    suspend fun createMatch(matchId: UserCard)
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

    override fun observeAllUsers(onUsersChanged : (List<UserCard>) -> Unit) {
        userServices.observeAllUsers(onUsersChanged)
    }

    override suspend fun createMatch(match: UserCard) {
        userServices.createMatch(match)
    }

}