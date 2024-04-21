package com.gameconnect.registerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gameconnect.databinding.FragmentRegisterPlataformsBinding

class PlatformsFragment: Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentRegisterPlataformsBinding.inflate(inflater, container, false)
            return binding.root
        }

        companion object{
            @JvmStatic
            fun newInstance() = PlatformsFragment()
        }
}