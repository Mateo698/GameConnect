package com.gameconnect.services

import com.gameconnect.model.Game
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class GameServices {

    suspend fun getGamesByTitle(title:String): List<Game> {
        val db = Firebase.firestore
        val games = db.collection("games")
            .whereEqualTo("title", title)
            .get()
            .await()
            .toObjects(Game::class.java)
        return games
    }
}