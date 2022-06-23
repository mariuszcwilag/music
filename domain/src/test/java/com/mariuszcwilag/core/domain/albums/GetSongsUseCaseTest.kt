package com.mariuszcwilag.core.domain.albums

import androidx.paging.PagingData
import app.cash.turbine.test
import com.mariuszcwilag.test.shared.BusinessObject
import com.mariuszcwilag.test.shared.TestCoroutineRule
import com.mariuszcwilag.test.shared.getTestCoroutineDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetSongsUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var albumsRepository: AlbumsRepository

    @Suppress("unused") // Injected in test subject
    private val coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider()

    @InjectMockKs
    private lateinit var getSongsUseCase: GetSongsUseCase

    private val testScope = TestScope()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke success`() = testCoroutineRule.runTest {
        // Given
        val pagingData = PagingData.from(
            listOf(BusinessObject.emptySongDomainObject())
        )
        coEvery { albumsRepository.getPagedSongs() } returns flowOf(pagingData)

        // When
        getSongsUseCase.invoke(testScope).test {
            assertNotNull(this)
            //TODO Improve with the assertion of the awaitItem()
        }
        coVerify(exactly = 1) { albumsRepository.getPagedSongs() }
        confirmVerified(albumsRepository)
    }
}