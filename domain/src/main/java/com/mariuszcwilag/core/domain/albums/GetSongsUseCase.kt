package com.mariuszcwilag.core.domain.albums

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mariuszcwilag.core.domain.albums.model.SongPresentationObject
import com.mariuszcwilag.core.domain.albums.model.toSongPresentationObject
import com.mariuszcwilag.core.domain.albums.utils.CoroutineDispatcherProvider
import com.mariuszcwilag.core.domain.albums.utils.mapWithContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val albumsRepository: AlbumsRepository,
) {

    suspend fun invoke(coroutineScope: CoroutineScope): Flow<PagingData<SongPresentationObject>?> =
        albumsRepository.getPagedSongs().map { pagingData ->
            pagingData.mapWithContext(coroutineDispatcherProvider.default) {
                it.toSongPresentationObject()
            }
        }.cachedIn(coroutineScope)
}