package com.gameconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.User
import com.gameconnect.model.Chat
import com.gameconnect.model.Message
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class ChatViewModel(
    val userRepo: UserRepository = UserRepositoryImpl(),

) : ViewModel(){

    private val _chat = MutableLiveData<Chat?>()
    private val _otherUser = MutableLiveData<User?>()
    val chat : LiveData<Chat?> get() = _chat
    val otherUser : LiveData<User?> get() = _otherUser

    val username: String = ""


    fun getChatInfo(id:String){
        viewModelScope.launch {
            val currentUserId = Firebase.auth.currentUser?.uid
            val chatInfo = userRepo.loadChatInfo(id)
            _chat.value = chatInfo

            userRepo.observeChatMessages(id){
                chat.value!!.messages.add(it)
                _chat.value = chat.value
            }

            val otherUserId = if(chatInfo.userOne == currentUserId) chatInfo.userTwo else chatInfo.userOne
            val otherUser = userRepo.loadSpecificUser(otherUserId)
            _otherUser.value = otherUser
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            userRepo.sendMessage(message, chat.value!!.id)
        }
    }


}