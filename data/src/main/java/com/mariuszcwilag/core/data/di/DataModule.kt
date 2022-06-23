package com.mariuszcwilag.core.data.di

import android.content.Context
import androidx.room.Room
import com.mariuszcwilag.core.data.access.local.database.AppDatabase
import com.mariuszcwilag.core.data.access.remote.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "albums-database"
    ).build()

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Api =
        Retrofit.Builder()
            .baseUrl("https://static.leboncoin.fr/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
}