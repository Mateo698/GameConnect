package com.gameconnect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gameconnect.R
import com.gameconnect.model.Game
import com.gameconnect.viewholder.GameCardViewHolder
import com.squareup.picasso.Picasso

class GameCardAdapter: RecyclerView.Adapter<GameCardViewHolder>(){
    private var games: List<Game> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        layoutInflater.inflate(R.layout.profile_game_card, parent, false)
        val holder = GameCardViewHolder(layoutInflater.inflate(R.layout.profile_game_card, parent, false))
        return holder
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        holder.title.text = games[position].title
        Picasso.get().load(games[position].thumbnail).into(holder.image)

    }

    fun setGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }


}