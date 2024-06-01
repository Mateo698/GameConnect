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

    suspend fun getAllGames(): List<Game> {
        val db = Firebase.firestore
        val games = db.collection("games")
            .get()
            .await()
            .toObjects(Game::class.java)
        return games
    }

    suspend fun getGamesByList(list: List<String>): List<Game> {
        val db = Firebase.firestore
        val games = db.collection("games")
            .whereIn("id", list)
            .get()
            .await()
            .toObjects(Game::class.java)
        return games
    }
}