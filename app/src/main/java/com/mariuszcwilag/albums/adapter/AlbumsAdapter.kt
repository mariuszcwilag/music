package com.mariuszcwilag.albums.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mariuszcwilag.core.domain.albums.model.SongPresentationObject

class AlbumsAdapter : PagingDataAdapter<SongPresentationObject, ViewHolder>(DataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlbumViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is AlbumViewHolder) {
            holder.bind(getItem(position) as SongPresentationObject)
        }
    }

    private class DataDiffCallback : DiffUtil.ItemCallback<SongPresentationObject>() {

        override fun areItemsTheSame(oldItem: SongPresentationObject, newItem: SongPresentationObject): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SongPresentationObject, newItem: SongPresentationObject): Boolean = oldItem == newItem
    }
}
