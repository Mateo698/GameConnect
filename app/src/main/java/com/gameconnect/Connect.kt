package com.gameconnect

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gameconnect.databinding.FragmentConnectBinding
import com.gameconnect.databinding.FragmentProfileBinding
import com.gameconnect.domain.model.UserCard
import com.gameconnect.services.FileServices
import com.gameconnect.viewmodel.ProfileViewModel
import com.gameconnect.viewmodel.UsersViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class Connect : Fragment() {

    private var _binding: FragmentConnectBinding? = null
    private val binding get() = _binding!!

    private val usersViewModel : UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fileServices = FileServices()
        val adapter = UserAdapter(fileServices)
        binding.cardsList.layoutManager = LinearLayoutManager(context)
        binding.cardsList.adapter = adapter

        usersViewModel.userCards.observe(viewLifecycleOwner, Observer { users ->
            users?.let { adapter.submitList(it) }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

