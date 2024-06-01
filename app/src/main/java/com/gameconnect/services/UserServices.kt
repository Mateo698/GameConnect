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

    fun observeAllUsers(onUsersChanged: (List<UserCard>) -> Unit) {
        val currentUserId = Firebase.auth.uid!!

        Firebase.firestore.collection("users").get().addOnSuccessListener { snapshot ->
            val users = mutableListOf<UserCard>()
            for (document in snapshot.documents) {
                val userCard = document.toObject(UserCard::class.java)
                if (userCard != null && userCard.id != currentUserId) {
                    users.add(userCard)
                }
            }

            Firebase.firestore.collection("users").document(currentUserId).collection("matches").get()
                .addOnSuccessListener { matchSnapshot ->
                    val matchedUserIds = matchSnapshot.documents.map { it.id }
                    val filteredUsers = users.filterNot { it.id in matchedUserIds }
                    onUsersChanged(filteredUsers)
                }
                .addOnFailureListener { matchError ->
                    Log.w(">>>", "Failed to fetch matches.", matchError)
                }
        }
            .addOnFailureListener { e ->
                Log.w(">>>", "Failed to fetch users.", e)
            }

    }


    suspend fun updateProfileImage(filename: String) {
        Firebase.firestore.collection("users").document(
            Firebase.auth.uid!!
        ).update("profilePic", filename).await()
    }

    suspend fun createMatch(match: UserCard){
        val currentUser = Firebase.auth.uid
        if (currentUser != null) {
            Firebase.firestore.collection("users").document(currentUser)
                .collection("matches").document(match.id)
                .set(mapOf(match.username to match.id))
                .await()
        }
    }

    suspend fun getGameTitles(gameIds: List<String>): List<String> {
        val titles = mutableListOf<String>()
        for (id in gameIds) {
            val document = Firebase.firestore.collection("games").document(id).get().await()
            val title = document.getString("title")
            if (title != null) {
                titles.add(title)
            }
        }
        return titles
    }
}


