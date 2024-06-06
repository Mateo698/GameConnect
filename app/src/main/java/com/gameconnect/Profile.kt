package com.gameconnect

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gameconnect.adapter.GameCardAdapter
import com.gameconnect.adapter.PlatformAdapter
import com.gameconnect.databinding.FragmentProfileBinding
import com.gameconnect.services.UserServices
import com.gameconnect.viewmodel.ProfileViewModel
import com.gameconnect.viewmodel.ViewProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private val viewProfileModel : ViewProfileViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.auth.currentUser?.let {
            viewModel.loadUser()

            binding.logoutBtn.setOnClickListener {
                viewModel.signout()
                val loginIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(loginIntent)
                requireActivity().finish()
            }

        } ?: run {
            Log.e(">>>", "User is not authenticated")
            val loginIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginIntent)
            requireActivity().finish()
        }

        requestPermissions(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ), 1)

        val galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val uri: Uri? = it.data?.data
                uri?.let {
                    viewModel.updateProfileImage(uri)
                }
            }
        }

        binding.userImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }

        val adapter = GameCardAdapter()
        val platformAdapter = PlatformAdapter()

        binding.gamesContainer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.gamesContainer.adapter = adapter

        binding.platformsContainer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.platformsContainer.adapter = platformAdapter



        viewModel.userState.observe(viewLifecycleOwner) {
            it.profilePic?.let {
                Glide.with(this@Profile).load(it).into(binding.userImageView)
            }

            platformAdapter.setPlatforms(it.platforms)
            binding.userDescriptionTV.text = it.biography
            binding.usernameTV.text = it.username
            binding.scheduleTV.text = it.time

            it.games.let { gameIds ->
                viewModel.loadGames(gameIds).observe(viewLifecycleOwner) { games ->
                    adapter.setGames(games)
                }

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
