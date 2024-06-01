package com.gameconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.User
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class ChatViewModel(
    val userRepo: UserRepository = UserRepositoryImpl(),

) : ViewModel(){

    /*Live data de la informacion del chat*/

    /*Live data del otro usuario*/
    private val _otherUser = MutableLiveData<User?>()
    val otherUser : LiveData<User?> get() = _otherUser

    val username: String = ""


    fun getChatInfo(id:String){
        viewModelScope.launch {
            val chatInfo = userRepo.loadChatInfo(id)
            val otherUserId = if(chatInfo.userOne == username) chatInfo.userTwo else chatInfo.userOne
            val otherUser = userRepo.loadSpecificUser(otherUserId)
            _otherUser.value = otherUser
        }
    }



}