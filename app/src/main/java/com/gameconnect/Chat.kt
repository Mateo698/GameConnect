package com.gameconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.databinding.FragmentChatBinding
import com.gameconnect.databinding.FragmentConnectBinding
import com.gameconnect.services.FileServices
import com.gameconnect.viewmodel.ProfileViewModel
import com.gameconnect.viewmodel.UsersViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class Chat : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.auth.currentUser?.let {
            viewModel.loadUser()

        } ?: run {
            Log.e(">>>", "User is not authenticated")
            val loginIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginIntent)
            requireActivity().finish()
        }

        viewModel.userState.observe(viewLifecycleOwner){
            binding.messageTV.text = "${it.username}, this are your connections"
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}