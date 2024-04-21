package com.gameconnect.registerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gameconnect.databinding.FragmentRegisterGenresBinding
import com.gameconnect.viewmodel.RegisterViewModel

class GenreFragment: Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterGenresBinding.inflate(inflater, container, false)

        binding.shooterCheckBox.setOnClickListener(){
            viewModel.addGenre("Shooter")
        }

        binding.sportsCheckBox2.setOnClickListener(){
            viewModel.addGenre("RPG")
        }

        binding.sportsCheckBox2.setOnClickListener(){
            viewModel.addGenre("Sports")
        }

        binding.survivalCheckBox5.setOnClickListener(){
            viewModel.addGenre("Survival")
        }

        binding.raceCheckBox7.setOnClickListener(){
            viewModel.addGenre("Racing")
        }

        binding.fightCheckBox4.setOnClickListener(){
            viewModel.addGenre("Fighting")
        }

        binding.mobaCheckBox3.setOnClickListener(){
            viewModel.addGenre("MOBA")
        }




        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = GenreFragment()
    }
}