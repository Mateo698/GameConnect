package com.gameconnect.registerFragments

import android.os.Bundle
import android.text.Editable
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

        binding.bioET.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel){
            override fun onTextChanged(target: RegisterViewModel, s: Editable){
                viewModel.setBiography(s.toString())
            }
        })

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = BioFragment()
    }

}