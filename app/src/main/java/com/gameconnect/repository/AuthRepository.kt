package com.gameconnect.repository

import com.gameconnect.domain.model.AppAuthState
import com.gameconnect.model.User
import com.gameconnect.services.AuthServices
import com.gameconnect.services.UserServices
import com.google.firebase.auth.FirebaseAuthException

interface AuthRepository {
    suspend fun login(email: String, pass: String) : AppAuthState
    suspend fun register(user: User, pass: String) : AppAuthState
}

class AuthRepositoryImpl(
    val authServices: AuthServices = AuthServices(),
    val userServices: UserServices = UserServices()
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

    override suspend fun register(user: User, pass: String): AppAuthState {
        try {
            val result = authServices.register(user.email, pass)
            result.user?.let {
                user.id = it.uid
                userServices.createUser(user)
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