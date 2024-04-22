package com.gameconnect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameconnect.model.User

class RegisterViewModel : ViewModel() {
    val page:MutableLiveData<Int> = MutableLiveData<Int>(0)
    val user:MutableLiveData<User> = MutableLiveData<User>(User("", listOf(), listOf(), listOf(), listOf(), "", "", ""))

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

    fun setBiography(biography: String) {
       //user.value = user.value?.copy(biography = biography)
    }

    fun addTags(gamertag: String) {
        val gamertags = user.value?.gamertags!!.toMutableList()
        gamertags.add(gamertag)
        user.value = user.value?.copy(gamertags = gamertags)
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