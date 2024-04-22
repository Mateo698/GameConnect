package com.gameconnect.registerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterBioBinding
import com.gameconnect.viewmodel.RegisterViewModel
import android.view.KeyEvent
import java.util.zip.Inflater

class BioFragment : Fragment(){

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val binding = FragmentRegisterBioBinding.inflate(inflater, container, false)

        binding.bioET.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                val biography = binding.bioET.text.toString().trim()

                viewModel.setBiography(biography)

                return@setOnKeyListener true
            }
            false
        }

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = BioFragment()
    }

}