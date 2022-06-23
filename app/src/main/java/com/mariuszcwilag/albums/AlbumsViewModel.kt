package com.mariuszcwilag.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mariuszcwilag.core.domain.albums.GetSongsUseCase
import com.mariuszcwilag.core.domain.albums.model.SongPresentationObject
import com.mariuszcwilag.core.domain.albums.utils.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getSongsUseCase: GetSongsUseCase,
) : ViewModel() {

    companion object {
        private const val DELAY_CONTENT_DISPLAY = 100L
        private const val DELAY_ERROR_DISPLAY = 500L
    }

    private val _albumsStateLiveData = MutableLiveData<AlbumsState>()
    val albumsStateLiveData: LiveData<AlbumsState> = _albumsStateLiveData

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            println("Albums: I'm working in thread ${Thread.currentThread().name}")
            getSongsUseCase.invoke(viewModelScope).onStart {
                _albumsStateLiveData.postValue(AlbumsState.Loading)
            }.collect { data ->
                if (data != null) {
                    delay(DELAY_CONTENT_DISPLAY)
                    _albumsStateLiveData.postValue(AlbumsState.Success(data))
                } else {
                    delay(DELAY_ERROR_DISPLAY)
                    _albumsStateLiveData.postValue(AlbumsState.Error)
                }
            }
        }
    }

    sealed interface AlbumsState {
        data class Success(val data: PagingData<SongPresentationObject>) : AlbumsState
        object Error : AlbumsState
        object Loading : AlbumsState
    }
}