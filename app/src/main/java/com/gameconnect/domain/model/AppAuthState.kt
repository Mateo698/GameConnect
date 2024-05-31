package com.gameconnect.domain.model

sealed class AppAuthState {
    data class Loading(val message: String) : AppAuthState()
    data class Error(val message: String) : AppAuthState()
    data class Success(val message: String) : AppAuthState()
}