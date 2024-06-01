package com.gameconnect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gameconnect.R
import com.gameconnect.viewholder.PlatformViewHolder

class PlatformAdapter : Adapter<PlatformViewHolder>(){

    fun setPlatforms(platforms: List<String>){
        this.platforms = platforms
        notifyDataSetChanged()
    }

    private var platforms: List<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        layoutInflater.inflate(R.layout.platform_tag, parent, false)
        val holder = PlatformViewHolder(layoutInflater.inflate(R.layout.platform_tag, parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        holder.title.text = platforms[position]

    }

    override fun getItemCount(): Int {
        return platforms.size
    }



}