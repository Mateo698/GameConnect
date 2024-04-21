package com.gameconnect.registerFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterUsernameBinding
import com.gameconnect.viewmodel.RegisterViewModel

class UsernameFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterUsernameBinding.inflate(inflater, container, false)

        binding.nametag.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel) {
            override fun onTextChanged(target: RegisterViewModel, s: Editable) {
                viewModel.setUsername(s.toString())
            }
        })



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = UsernameFragment()
    }
}

abstract class TextChangedListener<T>(private val target: T) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        this.onTextChanged(target, s)
    }

    abstract fun onTextChanged(target: T, s: Editable)
}
