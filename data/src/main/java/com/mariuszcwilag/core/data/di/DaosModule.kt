package com.mariuszcwilag.core.data.di

import com.mariuszcwilag.core.data.access.local.database.AppDatabase
import com.mariuszcwilag.core.data.access.local.database.SongsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAuthorDao(database: AppDatabase): SongsDao = database.songsDao()
}