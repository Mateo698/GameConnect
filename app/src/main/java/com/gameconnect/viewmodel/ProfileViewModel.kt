package com.gameconnect.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.User
import com.gameconnect.model.Game
import com.gameconnect.repository.AuthRepository
import com.gameconnect.repository.AuthRepositoryImpl
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import com.gameconnect.services.GameServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProfileViewModel (

    val userRepo: UserRepository = UserRepositoryImpl(),
    val authRepo: AuthRepository = AuthRepositoryImpl()

) : ViewModel() {

    //State
    private val _userState = MutableLiveData<User>()
    val userState:LiveData<User> get() = _userState
    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games
    val gameService: GameServices = GameServices()

    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO){
            val user = userRepo.loadUser()
            user?.let {
                withContext(Dispatchers.Main) {
                    _userState.value = it
                }
            }
        }
    }

    fun loadGames(gameIds: List<String>): LiveData<List<Game>> {
        viewModelScope.launch {
            val games = gameService.getGamesByList(gameIds)
            _games.value = games
        }
        return games
    }

    fun updateProfileImage(uri: Uri){
        viewModelScope.launch(Dispatchers.IO){
            val id = UUID.randomUUID().toString()
            userRepo.updateProfileImage(uri, id)
            loadUser()
        }
    }

    fun signout(){
        authRepo.signout()
    }
}