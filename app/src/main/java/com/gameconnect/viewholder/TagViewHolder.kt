package com.gameconnect.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gameconnect.databinding.GenreTagBinding

class TagViewHolder(root:View) : ViewHolder(root){

    private val binding = GenreTagBinding.bind(root)
    val title = binding.tagTitle
    fun bind(tag: String) {

    }





}