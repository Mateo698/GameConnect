package com.gameconnect.services

import android.util.Log
import com.gameconnect.domain.model.User
import com.gameconnect.domain.model.UserCard
import com.gameconnect.model.Chat
import com.gameconnect.model.Game
import com.gameconnect.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QueryDocumentSnapshot
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
        val db = Firebase.firestore

        db.collection("users").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(">>>", "Failed to fetch users.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val users = mutableListOf<UserCard>()
                for (document in snapshot.documents) {
                    val userCard = document.toObject(UserCard::class.java)
                    if (userCard != null && userCard.id != currentUserId) {
                        users.add(userCard)
                    }
                }

                db.collection("users").document(currentUserId).addSnapshotListener { matchSnapshot, matchError ->
                    if (matchError != null) {
                        Log.w(">>>", "Failed to fetch matches.", matchError)
                        return@addSnapshotListener
                    }

                    if (matchSnapshot != null && matchSnapshot.exists()) {
                        val matchedUserIds = (matchSnapshot["matches"] as? Map<String, String>)?.values ?: emptySet<String>()
                        val filteredUsers = users.filterNot { it.id in matchedUserIds }
                        Log.d("matchesFilter", "$matchedUserIds")
                        onUsersChanged(filteredUsers)
                    }
                }
            } else {
                onUsersChanged(emptyList())
            }
            }
    }

    suspend fun updateProfileImage(filename: String) {
        Firebase.firestore.collection("users").document(
            Firebase.auth.uid!!
        ).update("profilePic", filename).await()
    }

    suspend fun observeUserChats(userId: String): List<Chat> {
        val chats = mutableListOf<Chat>()
        try {
            val result = Firebase.firestore.collection("matches").where(
                Filter.or(
                    Filter.equalTo("userOne", userId),
                    Filter.equalTo("userTwo", userId)
                )
            ).get().await().toObjects(Chat::class.java)
            for (document in result) {
                chats.add(document)
            }
        } catch (exception: Exception) {

        }

        return chats
    }

    suspend fun getUsers(toList: List<String>): List<User> {
        val users = mutableListOf<User>()
        try {
            val result = Firebase.firestore.collection("users").whereIn("id", toList).get().await()
            for (document in result) {
                val user = document.toObject<User>()
                user.profilePic?.let {
                    user.profilePic = FileServices().downloadImage(it).toString()
                }
                users.add(user)
                Log.e(">>>", user.toString())
            }
        } catch (exception: Exception) {
            Log.w(">>>", "Error getting documents ", exception)
        }
        return users

    }

    suspend fun loadChatInfo(id: String): Chat {
        val chat = Firebase.firestore.collection("matches").document(id).get().await().toObject<Chat>()
        return chat!!
    }

    suspend fun createMatch(match: UserCard) {
        val currentUser = Firebase.auth.uid
        if (currentUser != null) {
            val matchData = mapOf(
                "dateMatched" to com.google.firebase.Timestamp.now(),
                "userOne" to currentUser,
                "userTwo" to match.id
            )

            val newMatchRef = Firebase.firestore.collection("matches").add(matchData).await()
            val matchId = newMatchRef.id
            newMatchRef.update("id", matchId).await()

            val currentUserRef = Firebase.firestore.collection("users").document(currentUser)
            val matchUserRef = Firebase.firestore.collection("users").document(match.id)

            val currentUserSnapshot = currentUserRef.get().await()
            val currentMatches = currentUserSnapshot.get("matches") as? MutableMap<String, String> ?: mutableMapOf()

            val matchSnapshot = matchUserRef.get().await()
            val matchMatches = matchSnapshot.get("matches") as? MutableMap<String, String> ?: mutableMapOf()

            currentMatches["id"] = match.id
            matchMatches["id"] = currentUser

            currentUserRef.update("matches", currentMatches).await()
            matchUserRef.update("matches", matchMatches).await()

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

    suspend fun sendMessage(message: Message, chatId: String) {
        val message = Firebase.firestore.collection("matches").document(chatId).collection("messages").add(message).await();
        message.update("id", message.id).await()
    }

    suspend fun observeChatMessages(
        function: (QueryDocumentSnapshot) -> Unit,
        chatId: String
    ) {
        Firebase.firestore.collection("matches").document(chatId).collection("messages")
            .orderBy("date")
            .addSnapshotListener { snapshot, error ->
                snapshot?.documentChanges?.forEach { change ->
                    when (change.type) {
                        com.google.firebase.firestore.DocumentChange.Type.ADDED -> {
                            function(change.document)
                        }
                        else -> {
                        }
                    }
                }
            }

    }


}


