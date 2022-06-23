package com.mariuszcwilag.albums

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mariuszcwilag.albums.utils.observeForTesting
import com.mariuszcwilag.core.domain.albums.GetSongsUseCase
import com.mariuszcwilag.test.shared.BusinessObject
import com.mariuszcwilag.test.shared.TestCoroutineRule
import com.mariuszcwilag.test.shared.getTestCoroutineDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AlbumsViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Suppress("unused") // Injected in test subject
    private val coroutineDispatcherProvider = testCoroutineRule.getTestCoroutineDispatcherProvider()

    @MockK
    private lateinit var getSongsUseCase: GetSongsUseCase

    @InjectMockKs
    private lateinit var albumsViewModel: AlbumsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetch albums is success`() = testCoroutineRule.runTest {
        // Given
        val pagingData = PagingData.from(
            listOf(BusinessObject.emptySongPresentationObject())
        )
        coEvery { getSongsUseCase.invoke(albumsViewModel.viewModelScope) } returns flowOf(pagingData)

        // When
        albumsViewModel.albumsStateLiveData.observeForTesting(this) {
            assertNotNull(it)
            assert(true)
            // TODO improve testing of live data value
        }

        coVerify(exactly = 1) {
            getSongsUseCase.invoke(albumsViewModel.viewModelScope)
        }
    }
}