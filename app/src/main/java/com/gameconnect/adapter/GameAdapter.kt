package com.gameconnect.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gameconnect.R
import com.gameconnect.SelectGameActivity
import com.gameconnect.model.Game
import com.gameconnect.viewholder.GameViewHolder
import com.squareup.picasso.Picasso

class GameAdapter(
    var games:ArrayList<Game> = arrayListOf(),
    var activity: Activity? = null
): Adapter<GameViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.game, parent, false)
        return GameViewHolder(root)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.genre.text = games[position].genre
        holder.title.text = games[position].title
        //holder.thumbnail.setImageURI(Uri.parse(games[position].thumbnail))
        Picasso.get().load(games[position].thumbnail).into(holder.thumbnail)
        holder.background.setOnClickListener {
            val intent = Intent()

            intent.apply {
                putExtra("game", games[position].title)
                putExtra("genre", games[position].genre)
                putExtra("thumbnail", games[position].thumbnail)
            }

            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()

        }
    }

    override fun getItemCount(): Int {
        return games.size
    }
}



