package com.gameconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.User
import com.gameconnect.model.Game
import com.gameconnect.repository.GameRepository
import com.gameconnect.repository.GameRepositoryImpl
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewProfileViewModel (
    val userRepo: UserRepository = UserRepositoryImpl(),
    val gameRepo: GameRepository = GameRepositoryImpl()

): ViewModel() {

        private val _userState = MutableLiveData<User>()
        private val _games = MutableLiveData<List<Game>>()
        val userState: LiveData<User> get() = _userState
        val games: LiveData<List<Game>> get() = _games

        fun loadSpecificUser(id: String) {
            viewModelScope.launch(Dispatchers.IO){
                val loadedUser = userRepo.loadSpecificUser(id)
                loadedUser?.let {
                    withContext(Dispatchers.Main) {
                        _userState.value = it
                    }
                    if(it.games != null){
                        val games = gameRepo.getGamesByList(it.games)
                        withContext(Dispatchers.Main) {
                            _games.value = games
                        }
                    }
                }
            }
        }


}