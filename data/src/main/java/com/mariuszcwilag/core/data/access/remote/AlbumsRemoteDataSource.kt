package com.mariuszcwilag.core.data.access.remote

import com.mariuszcwilag.core.data.access.remote.network.Api
import com.mariuszcwilag.core.data.access.remote.network.model.SongResponse
import javax.inject.Inject

class AlbumsRemoteDataSource @Inject constructor(private val api: Api) {
    suspend fun getSongs(): Result<List<SongResponse>?> =
        try {
            println("Albums: I'm working in thread ${Thread.currentThread().name}")
            val result = api.getSongs()
            if (result.isSuccessful) {
                Result.success(result.body())
            } else {
                Result.failure(Exception("Data Error!"))
            }
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
}