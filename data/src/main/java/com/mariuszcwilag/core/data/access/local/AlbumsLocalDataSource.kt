package com.mariuszcwilag.core.data.access.local

import com.mariuszcwilag.core.data.access.local.database.SongEntity
import com.mariuszcwilag.core.data.access.local.database.SongsDao
import javax.inject.Inject

class AlbumsLocalDataSource @Inject constructor(private val songsDao: SongsDao) {
    fun getPagedSongs() = songsDao.getPagedSongs()
    fun insertAll(songs: List<SongEntity>) = songsDao.insertAll(songs)
}