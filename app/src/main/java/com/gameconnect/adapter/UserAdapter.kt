package com.gameconnect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gameconnect.databinding.PlayerCardViewBinding
import com.gameconnect.domain.model.UserCard
import com.gameconnect.services.FileServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserAdapter(
    private val fileServices: FileServices
) : ListAdapter<UserCard, UserAdapter.UserViewHolder>(UserCardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = PlayerCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, fileServices)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class UserViewHolder(
        private val binding: PlayerCardViewBinding,
        private val fileServices: FileServices
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserCard) {
            binding.username.text = user.username
            binding.platform.text = user.platform
            binding.games.text = user.games.joinToString(", ")
            binding.descriptionTV.text = user.biography

            // Use a coroutine to download the image URL
            CoroutineScope(Dispatchers.Main).launch {
                val uri = try {
                    user.profilePic?.let { fileServices.downloadImage(it) }
                } catch (e: Exception) {
                    null
                }

                // Load the image with Glide
                Glide.with(binding.playerImage.context)
                    .load(uri)
                    .placeholder(R.drawable.ic_launcher_foreground) // Default image if not available
                    .error(R.drawable.ic_launcher_background) // Default image on error
                    .into(binding.playerImage)
            }
        }
    }

    class UserCardDiffCallback : DiffUtil.ItemCallback<UserCard>() {
        override fun areItemsTheSame(oldItem: UserCard, newItem: UserCard): Boolean {
            return oldItem.username == newItem.username // Adjust this based on unique identifier
        }

        override fun areContentsTheSame(oldItem: UserCard, newItem: UserCard): Boolean {
            return oldItem == newItem
        }
    }
}
