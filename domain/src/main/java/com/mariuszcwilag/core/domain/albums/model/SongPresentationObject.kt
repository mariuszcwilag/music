package com.mariuszcwilag.core.domain.albums.model

data class SongPresentationObject(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val imageUrl: String,
)