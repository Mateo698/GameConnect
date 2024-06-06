package com.gameconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.adapter.ChatAdapter
import com.gameconnect.databinding.FragmentChatBinding
import com.gameconnect.registerFragments.GenreFragment
import com.gameconnect.viewmodel.ProfileViewModel
import com.gameconnect.viewmodel.UsersViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class ChatsFragment : Fragment() {

    private val viewModel: UsersViewModel by activityViewModels()

    private val authViewModel: ProfileViewModel by viewModels()

    private var chatAdapter = ChatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        viewModel.fetchUserChats(Firebase.auth.currentUser!!.uid)

        Firebase.auth.currentUser?.let {
            authViewModel.loadUser()
        }

        authViewModel.userState.observe(viewLifecycleOwner){
            binding.messageTV.text = "${it.username}, this are your connections"
        }

        binding.chatsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.chatsRV.adapter = chatAdapter

        viewModel.chatItems.observe(viewLifecycleOwner){
            chatAdapter.setChats(it)
            Log.e("ChatFragment", "Chat items: $it")
            chatAdapter.notifyDataSetChanged()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = GenreFragment()
    }}