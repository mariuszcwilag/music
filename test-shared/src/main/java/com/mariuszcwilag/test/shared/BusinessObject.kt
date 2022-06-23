package com.mariuszcwilag.test.shared

import com.mariuszcwilag.core.domain.albums.model.SongDomainObject
import com.mariuszcwilag.core.domain.albums.model.SongPresentationObject

object BusinessObject {
    fun emptySongDomainObject() = SongDomainObject(
        id = 1,
        albumId = 1,
        title = "TITLE",
        thumbnailUrl = "THUMBNAIL_URL",
        url = "URL"
    )

    fun emptySongPresentationObject() = SongPresentationObject(
        id = 1,
        title = "TITLE",
        description = "DESCRIPTION",
        thumbnailUrl = "THUMBNAIL_URL",
        imageUrl = "URL"
    )
}