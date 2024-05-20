package com.gameconnect.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.model.User
import com.gameconnect.repository.UserRepository
import com.gameconnect.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProfileViewModel (

    val  userRepo: UserRepository = UserRepositoryImpl()

) : ViewModel() {

    //State
    private val _userState = MutableLiveData<User>()
    val userState:LiveData<User> get() = _userState

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

    fun updateProfileImage(uri: Uri){
        viewModelScope.launch(Dispatchers.IO){
            val id = UUID.randomUUID().toString()
            userRepo.updateProfileImage(uri, id)
            loadUser()
        }
    }
}