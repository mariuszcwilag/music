package com.mariuszcwilag.albums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mariuszcwilag.albums.R
import com.mariuszcwilag.albums.databinding.ItemViewAlbumBinding
import com.mariuszcwilag.core.domain.albums.model.SongPresentationObject

class AlbumViewHolder private constructor(
    private val binding: ItemViewAlbumBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(parent: ViewGroup) = AlbumViewHolder(
            ItemViewAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(song: SongPresentationObject) {
        // Title
        binding.albumTitle.text = song.title

        // Description
        binding.albumDescription.text = song.description

        // Image
        binding.albumImage.load(song.thumbnailUrl) {
            crossfade(true)
            placeholder(R.drawable.image_placeholder)
        }
    }
}