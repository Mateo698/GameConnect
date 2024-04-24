package com.gameconnect.registerFragments

import android.os.Bundle
import android.text.Editable
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

        binding.gamertagET.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel){
            override fun onTextChanged(target: RegisterViewModel, s: Editable){
                viewModel.setGamertags(listOf(s.toString()))
            }
        })

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = TagsFragment()
    }
}