package com.gameconnect.registerFragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterGamertagBinding
import com.gameconnect.viewmodel.RegisterViewModel

class TagsFragment: Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterGamertagBinding.inflate(inflater, container, false)

        binding.gamertagET.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val gamertag = binding.gamertagET.text.toString().trim()

                if (gamertag.isNotEmpty()) {
                    viewModel.addTags(gamertag)
                    binding.gamertagET.text.clear()
                }

                return@setOnKeyListener true
            }
            false
        }

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = TagsFragment()
    }
}