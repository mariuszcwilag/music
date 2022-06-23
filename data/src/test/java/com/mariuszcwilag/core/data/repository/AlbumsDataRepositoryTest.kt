package com.mariuszcwilag.core.data.repository

import app.cash.turbine.test
import com.mariuszcwilag.core.data.access.local.AlbumsLocalDataSource
import com.mariuszcwilag.core.data.access.remote.AlbumsRemoteDataSource
import com.mariuszcwilag.core.data.access.remote.network.model.SongResponse
import com.mariuszcwilag.test.shared.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumsDataRepositoryTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var localDataSource: AlbumsLocalDataSource

    @MockK
    private lateinit var remoteDataSource: AlbumsRemoteDataSource

    @InjectMockKs
    private lateinit var repository: AlbumsDataRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When get songs from Network returns success Then insert all songs in the database`() = testCoroutineRule.runTest {
        // Given
        coEvery { remoteDataSource.getSongs() } returns Result.success(listOf(emptySongResponse()))
        every { localDataSource.getPagedSongs() } returns com.mariuszcwilag.test.shared.TestSongsPagingSource()
        justRun { localDataSource.insertAll(any()) }

        // When
        repository.getPagedSongs().test {
            // Then
            assertNotNull(awaitEvent())
            cancelAndIgnoreRemainingEvents()
        }
        // Then
        coVerify(exactly = 1) { remoteDataSource.getSongs() }
        verify(exactly = 1) {
            localDataSource.getPagedSongs()
            localDataSource.insertAll(any())
        }
        confirmVerified(remoteDataSource, localDataSource)
    }

    @Test
    fun `When get songs from Network returns failure Then do not insert songs in the database`() = testCoroutineRule.runTest {
        // Given
        coEvery { remoteDataSource.getSongs() } returns Result.failure(Exception("Failure!"))
        every { localDataSource.getPagedSongs() } returns com.mariuszcwilag.test.shared.TestSongsPagingSource()
        justRun { localDataSource.insertAll(any()) }

        // When
        repository.getPagedSongs().test {
            // Then
            assertNotNull(awaitEvent())
            cancelAndIgnoreRemainingEvents()
        }
        // Then
        coVerify(exactly = 1) { remoteDataSource.getSongs() }
        verify(exactly = 1) { localDataSource.getPagedSongs() }
        verify(exactly = 0) { localDataSource.insertAll(any()) }
        confirmVerified(remoteDataSource, localDataSource)
    }

    private fun emptySongResponse(): SongResponse = SongResponse(
        id = 0,
        albumId = 1,
        title = "TITLE",
        url = "URL",
        thumbnailUrl = "THUMBNAIL_URL"
    )
}