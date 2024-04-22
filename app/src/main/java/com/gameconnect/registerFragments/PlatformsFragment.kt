package com.gameconnect.registerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterPlataformsBinding
import com.gameconnect.viewmodel.RegisterViewModel

class PlatformsFragment: Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentRegisterPlataformsBinding.inflate(inflater, container, false)
            binding.checkboxPS.setOnClickListener(){
                viewModel.addPlatform("PlayStation")
            }

            binding.checkboxXbox.setOnClickListener(){
                viewModel.addPlatform("Xbox")
            }

            binding.checkboxPC.setOnClickListener(){
                viewModel.addPlatform("PC")
            }

            binding.checkboxSwitch.setOnClickListener(){
                viewModel.addPlatform("Switch")
            }

            binding.checkboxMobile.setOnClickListener(){
                viewModel.addPlatform("Mobile")
            }

            return binding.root
        }

        companion object{
            @JvmStatic
            fun newInstance() = PlatformsFragment()
        }
}