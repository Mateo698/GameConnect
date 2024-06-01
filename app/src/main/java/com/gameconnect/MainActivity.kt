package com.gameconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.gameconnect.databinding.ActivityMainBinding
import com.gameconnect.viewmodel.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val viewmodel : ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(Connect())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.connect -> replaceFragment(Connect())
                R.id.profile -> replaceFragment(Profile())
                R.id.chat -> replaceFragment(ChatsFragment())

                else-> {

                }
            }
            true
        }

        Firebase.auth.currentUser?.let {
            viewmodel.loadUser()
            //Log the user into Logcat
            Log.e("User", it.uid)


        } ?: run {
            Log.e("User", "No user logged in")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}