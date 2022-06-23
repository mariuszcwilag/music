package com.mariuszcwilag.core.data.di

import com.mariuszcwilag.core.data.repository.AlbumsDataRepository
import com.mariuszcwilag.core.domain.albums.AlbumsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataBindsModule {

    @Binds
    fun bindsAlbumsRepository(implementation: AlbumsDataRepository): AlbumsRepository
}