package com.gameconnect.registerFragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterTimeBinding
import com.gameconnect.viewmodel.RegisterViewModel

class TimeFragment: Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterTimeBinding.inflate(inflater, container, false)

        binding.checkboxMonday.setOnClickListener(){
            viewModel.selectDayAvailable("Monday")
        }

        binding.checkboxTuesday.setOnClickListener(){
            viewModel.selectDayAvailable("Tuesday")
        }

        binding.checkboxWednesday.setOnClickListener(){
            viewModel.selectDayAvailable("Wednesday")
        }

        binding.checkboxThursday.setOnClickListener(){
            viewModel.selectDayAvailable("Thursday")
        }

        binding.checkboxFriday.setOnClickListener(){
            viewModel.selectDayAvailable("Friday")
        }

        binding.checkboxSaturday.setOnClickListener(){
            viewModel.selectDayAvailable("Saturday")
        }

        binding.checkboxSunday.setOnClickListener(){
            viewModel.selectDayAvailable("Sunday")
        }

        binding.hourET.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel){
            override fun onTextChanged(target: RegisterViewModel, s: Editable){
                viewModel.setHourAvailable(s.toString())
            }
        })

        binding.minuteET.addTextChangedListener(object : TextChangedListener<RegisterViewModel>(viewModel){
            override fun onTextChanged(target: RegisterViewModel, s: Editable){
                viewModel.setMinuteAvailable(s.toString())
            }
        })

        binding.checkboxAm.setOnClickListener(){
            viewModel.selectMeridiemAvailable("AM")
        }

        binding.checkboxPm.setOnClickListener(){
            viewModel.selectMeridiemAvailable("PM")
        }

        return binding.root
    }
    companion object{
        @JvmStatic
        fun newInstance() = TimeFragment()
    }
}