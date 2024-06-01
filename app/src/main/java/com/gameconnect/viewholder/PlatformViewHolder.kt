package com.gameconnect.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gameconnect.databinding.PlatformTagBinding

class PlatformViewHolder(root:View) : RecyclerView.ViewHolder(root){
    private val binding = PlatformTagBinding.bind(root)
    val title = binding.platformTitle

}