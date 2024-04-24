package com.gameconnect

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.adapter.GameAdapter
import com.gameconnect.databinding.ActivityRegisterBinding
import com.gameconnect.databinding.ActivitySelectGameBinding
import com.gameconnect.model.Game
import com.gameconnect.viewmodel.RegisterViewModel

class SelectGameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySelectGameBinding.inflate(layoutInflater)
    }

    val adapter = GameAdapter()

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.games.observe(this) {
            adapter.games = it as? ArrayList<Game> ?: arrayListOf()
            adapter.activity = this
            adapter.notifyDataSetChanged()
        }

        binding.searchButton.setOnClickListener {
            var title = binding.searchBar.text.toString()
            if(title.isEmpty()){
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getGamesByTitle(title)
            }
            //viewModel.getAllGames()
        }




        binding.recyclerView.adapter = adapter
    }
}