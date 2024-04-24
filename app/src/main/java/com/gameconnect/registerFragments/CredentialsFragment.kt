package com.gameconnect.registerFragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterCredentialsBinding
import com.gameconnect.viewmodel.RegisterViewModel


class CredentialsFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterCredentialsBinding.inflate(inflater, container, false)

        binding.emailRegisterET.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel){
            override fun onTextChanged(target: RegisterViewModel, s: Editable){
                viewModel.setEmail(s.toString())
            }
        })

        binding.passRegisterET.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel){
            override fun onTextChanged(target: RegisterViewModel, s: Editable){
                viewModel.setPassword(s.toString())
            }
        })


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CredentialsFragment()
    }
}


