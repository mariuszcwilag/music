package com.mariuszcwilag.test.shared

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mariuszcwilag.core.data.access.local.database.SongEntity

class TestSongsPagingSource : PagingSource<Int, SongEntity>() {
    override fun getRefreshKey(state: PagingState<Int, SongEntity>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SongEntity> {
        return LoadResult.Page(listOf(), 0, 1)
    }
}