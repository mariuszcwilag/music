package com.mariuszcwilag.core.domain.albums

import androidx.paging.PagingData
import com.mariuszcwilag.core.domain.albums.model.SongDomainObject
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun getPagedSongs(): Flow<PagingData<SongDomainObject>>
}