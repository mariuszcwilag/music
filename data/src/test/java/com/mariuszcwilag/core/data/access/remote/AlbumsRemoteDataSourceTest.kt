package com.mariuszcwilag.core.data.access.remote

import com.mariuszcwilag.core.data.access.remote.network.Api
import com.mariuszcwilag.test.shared.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class AlbumsRemoteDataSourceTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var api: Api

    @InjectMockKs
    private lateinit var remoteDataSource: AlbumsRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When api get songs is success Then result is success`() = testCoroutineRule.runTest {
        // Given
        coEvery { api.getSongs() } returns Response.success(listOf())

        // When
        val result = remoteDataSource.getSongs()

        // Then
        assertNotNull(result)
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) {
            api.getSongs()
        }
        confirmVerified()
    }

    @Test
    fun `When api get songs is error Then result is failure`() = testCoroutineRule.runTest {
        // Given
        coEvery { api.getSongs() } returns Response.error(404, byteArrayOf().toResponseBody())

        // When
        val result = remoteDataSource.getSongs()

        // Then
        assertNotNull(result)
        assertTrue(result.isFailure)
        coVerify(exactly = 1) {
            api.getSongs()
        }
        confirmVerified()
    }

    @Test
    fun `When api get songs throws exception Then result is failure`() = testCoroutineRule.runTest {
        // Given
        coEvery { api.getSongs() } throws IOException()

        // When
        val result = remoteDataSource.getSongs()

        // Then
        assertNotNull(result)
        assertTrue(result.isFailure)
        coVerify(exactly = 1) {
            api.getSongs()
        }
        confirmVerified()
    }
}