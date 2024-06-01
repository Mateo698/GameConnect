package com.gameconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.adapter.ChatAdapter
import com.gameconnect.databinding.FragmentChatBinding
import com.gameconnect.registerFragments.GenreFragment
import com.gameconnect.viewmodel.UsersViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class ChatsFragment : Fragment() {

    private val viewModel: UsersViewModel by activityViewModels()

    private var chatAdapter = ChatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        viewModel.fetchUserChats(Firebase.auth.currentUser!!.uid)

        binding.chatsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.chatsRV.adapter = chatAdapter

        viewModel.chatItems.observe(viewLifecycleOwner){
            chatAdapter.setChats(it)
            Log.e("ChatFragment", "Chat items: $it")
            chatAdapter.notifyDataSetChanged()
        }

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