package com.gameconnect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gameconnect.R
import com.gameconnect.viewholder.TagViewHolder

class TagAdapter : Adapter<TagViewHolder>(){

    private var tags: List<String> = arrayListOf()



    fun setTags(tags: List<String>){
        this.tags = tags
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        layoutInflater.inflate(R.layout.genre_tag, parent, false)
        val holder = TagViewHolder(layoutInflater.inflate(R.layout.genre_tag, parent, false))
        return holder
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.title.text = tags[position]
    }
}