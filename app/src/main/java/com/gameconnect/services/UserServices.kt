package com.gameconnect.services

import com.gameconnect.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserServices   {

    suspend fun createUser(user: User) {
        Firebase.firestore.collection("users").document(user.id).set(user).await()
    }

    suspend fun loadUser(id: String): DocumentSnapshot {
        val output = Firebase.firestore.collection("users").document(id).get().await()
        return output
    }


}