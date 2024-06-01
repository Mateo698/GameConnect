package com.gameconnect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameconnect.adapter.GameCardAdapter
import com.gameconnect.adapter.PlatformAdapter
import com.gameconnect.adapter.TagAdapter
import com.gameconnect.databinding.ActivityViewProfileBinding
import com.gameconnect.viewmodel.ViewProfileViewModel
import com.squareup.picasso.Picasso

class ViewProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityViewProfileBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: TagAdapter
    private lateinit var platformAdapter: PlatformAdapter
    private lateinit var gameAdapter: GameCardAdapter

    val viewmodel : ViewProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        adapter = TagAdapter()
        platformAdapter = PlatformAdapter()
        gameAdapter = GameCardAdapter()
        binding.genres.adapter = adapter
        binding.platforms.adapter = platformAdapter
        binding.games.adapter = gameAdapter
        binding.platforms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.genres.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.games.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.genres.setHasFixedSize(true)
        binding.platforms.setHasFixedSize(true)
        binding.games.setHasFixedSize(true)


        viewmodel.loadSpecificUser(intent.getStringExtra("id")!!)


        viewmodel.userState.observe(this){
            binding.aboutmeLabel.text = it.biography
            binding.scheduleLabel.text = it.time
            binding.usernameLabel.text = it.username
            adapter.setTags(it.genres)
            platformAdapter.setPlatforms(it.platforms)
            Picasso.get().load(it.profilePic).into(binding.imageView4)
        }

        viewmodel.games.observe(this){
            gameAdapter.setGames(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}