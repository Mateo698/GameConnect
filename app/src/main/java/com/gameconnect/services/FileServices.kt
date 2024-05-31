package com.gameconnect.services

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class FileServices {

    suspend fun uploadFile(uri: Uri, filename: String){
        Firebase.storage.reference
            .child("profile")
            .child("image")
            .child(filename)
            .putFile(uri)
            .await()
    }

    suspend fun downloadImage(id: String) : Uri {
        return Firebase.storage.reference
            .child("profile")
            .child("image")
            .child(id).downloadUrl.await()
    }

}