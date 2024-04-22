package com.gameconnect.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gameconnect.databinding.GameBinding

class GameViewHolder(root:View): ViewHolder(root) {
    private val binding = GameBinding.bind(root)

    val title = binding.titleTV
    val thumbnail = binding.imageView3
    val genre = binding.genreTV
}