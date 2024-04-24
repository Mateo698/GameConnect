package com.gameconnect.services

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthServices {

    suspend fun login(email: String, pass: String) : AuthResult {
        return Firebase.auth.signInWithEmailAndPassword(email, pass).await()
    }
}