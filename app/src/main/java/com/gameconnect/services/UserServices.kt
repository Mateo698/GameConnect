package com.gameconnect.services

import android.util.Log
import com.gameconnect.domain.model.User
import com.gameconnect.domain.model.UserCard
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UserServices   {

    suspend fun createUser(user: User) {
        Firebase.firestore.collection("users").document(user.id).set(user).await()
    }

    suspend fun loadUser(id: String): DocumentSnapshot {
        val output = Firebase.firestore.collection("users").document(id).get().await()
        return output
    }

    suspend fun observeAllUsers() : List<UserCard> {
        val users = mutableListOf<UserCard>()
        try {
            val result = Firebase.firestore.collection("users").get().await()
            for (document in result) {
                val userCard = document.toObject(UserCard::class.java)
                users.add(userCard)
            }
        } catch (exception: Exception) {
            Log.w(">>>", "Error getting documents ", exception)
        }
        return users
    }


    suspend fun updateProfileImage(filename: String) {
        Firebase.firestore.collection("users").document(
            Firebase.auth.uid!!
        ).update("profilePic", filename).await()
    }


}