package com.gameconnect.repository

import com.gameconnect.model.Game
import com.gameconnect.services.GameServices

interface GameRepository {
    suspend fun getGamesByTitle(title: String): List<Game>
    suspend fun getAllGames(): List<Game>

}

class GameRepositoryImpl(
    val gameServices: GameServices = GameServices()
): GameRepository {
    override suspend fun getGamesByTitle(title: String): List<Game> {
        return gameServices.getGamesByTitle(title)
    }

    override suspend fun getAllGames(): List<Game> {
        return gameServices.getAllGames()
    }
}
