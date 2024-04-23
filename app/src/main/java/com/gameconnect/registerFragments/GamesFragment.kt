package com.gameconnect.registerFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.SelectGameActivity
import com.gameconnect.adapter.GameAdapter
import com.gameconnect.databinding.FragmentRegisterGamesBinding
import com.gameconnect.model.Game
import com.gameconnect.viewmodel.RegisterViewModel

class GamesFragment: Fragment() {

        private val viewModel: RegisterViewModel by activityViewModels()

        val adapter = GameAdapter()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentRegisterGamesBinding.inflate(inflater, container, false)
            binding.selectedGames.layoutManager = LinearLayoutManager(requireContext())

            val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), this::onResult)
            binding.addButton.setOnClickListener {
                launcher.launch(Intent(requireContext(), SelectGameActivity::class.java))
            }

            viewModel.selectedGames.observe(viewLifecycleOwner){

                adapter.games = it as? ArrayList<Game> ?: arrayListOf()
                adapter.notifyDataSetChanged()
            }

            binding.selectedGames.adapter = adapter

            return binding.root
        }

        fun onResult(result: ActivityResult){
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                val game = Game("1",data?.getStringExtra("game") ?: "", data?.getStringExtra("genre") ?: "", "", "")
                viewModel.addGame(game)
            }
        }


        companion object{
            @JvmStatic
            fun newInstance() = GamesFragment()
        }
}