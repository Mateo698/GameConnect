package com.gameconnect.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gameconnect.databinding.PlatformTagBinding
import com.gameconnect.databinding.ProfileGameCardBinding

class GameCardViewHolder(root:View): ViewHolder(root){
    private val binding = ProfileGameCardBinding.bind(root)
    val title = binding.gameTitle
    val image = binding.gameImg

}