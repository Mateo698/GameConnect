package com.gameconnect

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gameconnect.databinding.FragmentChatBinding
import com.gameconnect.databinding.FragmentRegisterGamesBinding
import com.gameconnect.registerFragments.GenreFragment


class Chat : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.goToProfile.setOnClickListener(){
            val intent = Intent(activity, ViewProfileActivity::class.java)
            intent.putExtra("id", "gkYVIU9hXFSVC2zRkp6aoOyGAcX2")
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = GenreFragment()
    }}