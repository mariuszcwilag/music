package com.mariuszcwilag.core.data.access.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SongsDao {
    @Query("SELECT * FROM songs")
    fun getAll(): List<SongEntity>

    @Query("SELECT * FROM songs")
    fun getPagedSongs(): PagingSource<Int, SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(songs: List<SongEntity>)
}