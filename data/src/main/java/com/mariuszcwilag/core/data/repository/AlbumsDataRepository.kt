package com.mariuszcwilag.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.mariuszcwilag.core.data.access.local.AlbumsLocalDataSource
import com.mariuszcwilag.core.data.access.local.database.SongEntity
import com.mariuszcwilag.core.data.access.remote.AlbumsRemoteDataSource
import com.mariuszcwilag.core.data.access.remote.network.model.SongResponse
import com.mariuszcwilag.core.data.utils.PagingUtils
import com.mariuszcwilag.core.domain.albums.AlbumsRepository
import com.mariuszcwilag.core.domain.albums.model.SongDomainObject
import com.mariuszcwilag.core.domain.albums.utils.CoroutineDispatcherProvider
import com.mariuszcwilag.core.domain.albums.utils.mapWithContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsDataRepository @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val localDataSource: AlbumsLocalDataSource,
    private val remoteDataSource: AlbumsRemoteDataSource,
) : AlbumsRepository {

    override suspend fun getPagedSongs(): Flow<PagingData<SongDomainObject>> {
        println("Albums: I'm fetch some songs from network in thread ${Thread.currentThread().name}")
        // Fetch and insert songs to database if result is successful
        remoteDataSource.getSongs().getOrNull()?.let { songs ->
            println("Albums: I'm insert some songs to database in thread ${Thread.currentThread().name}")
            localDataSource.insertAll(songs.map(SongResponse::toSongEntity))
        }
        return Pager(
            config = PagingUtils.buildConfig(),
            pagingSourceFactory = { localDataSource.getPagedSongs() }
        ).flow.map { pagingData ->
            pagingData.mapWithContext(coroutineDispatcherProvider.default) {
                it.toSongDomainObject()
            }
        }
    }
}

private fun SongResponse.toSongEntity() =
    SongEntity(
        id = this.id,
        albumId = this.albumId,
        title = this.title,
        thumbnailUrl = this.thumbnailUrl,
        url = this.url,
    )

private fun SongEntity.toSongDomainObject() =
    SongDomainObject(
        id = this.id,
        albumId = this.albumId,
        title = this.title,
        thumbnailUrl = this.thumbnailUrl,
        url = this.url,
    )
