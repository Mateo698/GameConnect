package com.gameconnect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.User
import com.gameconnect.domain.model.UserCard
import com.gameconnect.model.Chat
import com.gameconnect.model.ChatItem
import com.gameconnect.model.Message
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class UsersViewModel (
    private val userRepository : UserRepository = UserRepositoryImpl()
    ) : ViewModel() {
        private val _userCards = MutableLiveData<List<UserCard>>()
        private val _userChats = MutableLiveData<List<Chat>>()
        private val _chatItems = MutableLiveData<List<ChatItem>>()
        val userCards: LiveData<List<UserCard>> get() = _userCards
        val userChats: LiveData<List<Chat>> get() = _userChats
        val chatItems: LiveData<List<ChatItem>> get() = _chatItems

    init {
        fetchAllUsers()
        fetchUserChats(Firebase.auth.currentUser!!.uid)
    }

    private fun fetchAllUsers() {
        userRepository.observeAllUsers { users ->
            _userCards.postValue(users)
        }
    }

    fun fetchUserChats(userId: String) {
        viewModelScope.launch {
            val chats = userRepository.observeUserChats(userId)
            convertChatsToChatItems(chats)

            _userChats.postValue(chats)
        }
    }

    private fun convertChatsToChatItems(chats: List<Chat>) {
        val chatItems = mutableListOf<ChatItem>()
        var userIds = mutableSetOf<String>()
        for (chat in chats) {
            if(Firebase.auth.currentUser!!.uid == chat.userOne) {
                userIds.add(chat.userTwo)
            } else {
                userIds.add(chat.userOne)
            }
        }

        viewModelScope.launch {
            var users : List<User> = userRepository.getUsers(userIds.toList())
            for (chat in chats) {
                var user = users.find { it.id == chat.userOne || it.id == chat.userTwo }
                Log.e("FOUND USER", user.toString())
                Log.e("FOUND CHAT", chat.toString())

                var chatItem = ChatItem(
                    user!!.profilePic?:"",
                    user.username,
                    chat.messages.lastOrNull() ?: Message("", ""),
                    chat.id
                )
                chatItems.add(chatItem)
            }
            Log.e(">>>", chatItems.toString())
            _chatItems.postValue(chatItems)
        }
        _chatItems.postValue(chatItems)
    }


    fun createMatch(match: UserCard){
        viewModelScope.launch {
            userRepository.createMatch(match)
        }
    }
}