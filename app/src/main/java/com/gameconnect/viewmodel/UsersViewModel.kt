package com.gameconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.UserCard
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class UsersViewModel (
    private val userRepository : UserRepository = UserRepositoryImpl()
    ) : ViewModel() {
        private val _userCards = MutableLiveData<List<UserCard>>()
        val userCards: LiveData<List<UserCard>> get() = _userCards

    init {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        userRepository.observeAllUsers { users ->
            _userCards.postValue(users)
        }
    }

    fun createMatch(match: UserCard){
        viewModelScope.launch {
            userRepository.createMatch(match)
        }
    }

    }