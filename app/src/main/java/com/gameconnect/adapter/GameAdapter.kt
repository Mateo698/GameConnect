package com.gameconnect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gameconnect.R
import com.gameconnect.model.Game
import com.gameconnect.viewholder.GameViewHolder

class GameAdapter: Adapter<GameViewHolder>() {
    private var games = listOf<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.game, parent, false)
        val holder = GameViewHolder(view)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        TODO()
    }

    override fun getItemCount(): Int {
        return games.size
    }
}

