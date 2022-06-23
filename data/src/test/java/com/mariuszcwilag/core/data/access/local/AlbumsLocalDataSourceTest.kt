package com.mariuszcwilag.core.data.access.local

import com.mariuszcwilag.core.data.access.local.database.SongsDao
import com.mariuszcwilag.test.shared.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumsLocalDataSourceTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var songsDao: SongsDao

    @InjectMockKs
    private lateinit var localDataSource: AlbumsLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When get paged songs Then ensure if songs dao is called`() = testCoroutineRule.runTest {
        // Given
        coEvery { songsDao.getPagedSongs() } returns com.mariuszcwilag.test.shared.TestSongsPagingSource()

        // When
        val result = localDataSource.getPagedSongs()

        // Then
        assertNotNull(result)
        coVerify(exactly = 1) {
            songsDao.getPagedSongs()
        }
        confirmVerified()
    }

    @Test
    fun `When insert all Then ensure if songs dao is called`() = testCoroutineRule.runTest {
        // Given
        justRun { songsDao.insertAll(any()) }

        // When
        val result = localDataSource.insertAll(listOf())

        // Then
        assertNotNull(result)
        coVerify(exactly = 1) {
            songsDao.insertAll(any())
        }
        confirmVerified()
    }
}