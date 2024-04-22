package com.gameconnect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.model.Game
import com.gameconnect.model.User
import com.gameconnect.repository.GameRepository
import com.gameconnect.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class RegisterViewModel(
    val gameRepository: GameRepository = GameRepositoryImpl(),
) : ViewModel() {
    val page:MutableLiveData<Int> = MutableLiveData<Int>(0)
    val user:MutableLiveData<User> = MutableLiveData<User>(User("", listOf(), listOf(), listOf(), listOf(), "", "", ""))
    val games:MutableLiveData<List<Game>> = MutableLiveData<List<Game>>(listOf())

    fun nextPage(){
        page.value = page.value?.plus(1)
    }

    fun previousPage(){
        page.value = page.value?.minus(1)
    }

    fun setUsername(username: String){
        user.value = user.value?.copy(username = username)
    }

    fun addGenre(genre: String){
        val genres = user.value?.genres!!.toMutableList()

        if(genres.contains(genre)){
            genres.remove(genre)
        }else{
            genres.add(genre)
        }
    }
    fun addPlatform(platform: String) {
        val platforms = user.value?.platforms!!.toMutableList()

        if (platforms.contains(platform)) {
            platforms.remove(platform)
        } else {
            platforms.add(platform)
        }

        user.value = user.value?.copy(platforms = platforms)
    }

    fun getGamesByTitle(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            val gamesFetched = gameRepository.getGamesByTitle(title)
            games?.let {
                withContext(Dispatchers.Main){
                    games.value = gamesFetched
                }
            }
        }
    }

    fun setPlatforms(platforms: List<String>){
        user.value = user.value?.copy(platforms = platforms)
    }

    fun setGames(games: List<String>){
        user.value = user.value?.copy(games = games)
    }

    fun setGamertags(gamertags: List<String>){
        user.value = user.value?.copy(gamertags = gamertags)
    }

    fun setEmail(email: String){
        user.value = user.value?.copy(email = email)
    }

    fun setPassword(password: String){
        user.value = user.value?.copy(password = password)
    }

    fun setTime(time: String){
        user.value = user.value?.copy(time = time)
    }

}