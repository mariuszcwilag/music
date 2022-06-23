package com.mariuszcwilag.core.data.access.remote.network

import com.mariuszcwilag.core.data.access.remote.network.model.SongResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit API declaration
 */
interface Api {
    @GET("img/shared/technical-test.json")
    suspend fun getSongs(): Response<List<SongResponse>>
}