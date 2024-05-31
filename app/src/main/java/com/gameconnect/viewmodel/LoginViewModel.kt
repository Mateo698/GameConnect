package com.gameconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.AppAuthState
import com.gameconnect.repository.AuthRepository
import com.gameconnect.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel (val repo:AuthRepository = AuthRepositoryImpl()) : ViewModel() {

    private val _authStatus = MutableLiveData<AppAuthState>()
    val authStatus: LiveData<AppAuthState> = _authStatus

    fun login(email: String, pass: String){
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main){
                _authStatus.value = AppAuthState.Loading("Loading...")
            }

            val status = repo.login(email, pass)
            withContext(Dispatchers.Main){_authStatus.value = status}
        }
    }
}