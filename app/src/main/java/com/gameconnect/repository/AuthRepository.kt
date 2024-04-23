package com.gameconnect.repository

import com.gameconnect.domain.model.AppAuthState
import com.gameconnect.services.AuthServices
import com.google.firebase.auth.FirebaseAuthException

interface AuthRepository {
    suspend fun login(email: String, pass: String) : AppAuthState
}

class AuthRepositoryImpl(
    val authServices: AuthServices = AuthServices()
) : AuthRepository {
    override suspend fun login(email: String, pass: String): AppAuthState {
        try {
            val result = authServices.login(email, pass)
            result.user?.let {
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong!")
            }
        }
        catch (ex: FirebaseAuthException) {
            return AppAuthState.Error(ex.errorCode)
        }
    }
}