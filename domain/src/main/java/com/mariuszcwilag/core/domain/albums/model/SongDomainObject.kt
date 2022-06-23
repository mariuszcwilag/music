package com.mariuszcwilag.core.domain.albums.model

data class SongDomainObject(
    val albumId: Int,
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val url: String,
)

fun SongDomainObject.toSongPresentationObject() =
    SongPresentationObject(
        id = this.id,
        title = this.title.uppercase(),
        description = "ALBUM ID: ${this.albumId}",
        thumbnailUrl = this.thumbnailUrl,
        imageUrl = this.url,
    )