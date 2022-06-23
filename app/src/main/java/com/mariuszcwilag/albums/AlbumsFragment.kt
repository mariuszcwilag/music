package com.mariuszcwilag.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariuszcwilag.albums.AlbumsViewModel.AlbumsState.Loading
import com.mariuszcwilag.albums.AlbumsViewModel.AlbumsState.Success
import com.mariuszcwilag.albums.adapter.AlbumsAdapter
import com.mariuszcwilag.albums.databinding.FragmentAlbumsBinding
import com.mariuszcwilag.albums.extension.removeBlinkOnNotify
import com.mariuszcwilag.albums.extension.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private val albumsViewModel: AlbumsViewModel by viewModels()

    private val albumAdapter by lazy { AlbumsAdapter() }

    private val binding by viewBinding(FragmentAlbumsBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_albums, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.albumsRecyclerView) {
            adapter = albumAdapter
            removeBlinkOnNotify()
            layoutManager = LinearLayoutManager(activity)
        }

        albumsViewModel.albumsStateLiveData.observe(viewLifecycleOwner) { result ->
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Loading -> showStatusMessage(getString(R.string.albums_state_loading))
                        is Success -> {
                            binding.albumsRecyclerView.isVisible = true
                            binding.albumsStatusText.isVisible = false
                            albumAdapter.submitData(result.data)
                        }
                        is Error -> showStatusMessage(getString(R.string.albums_state_error))
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun showStatusMessage(statusText: String) {
        binding.albumsRecyclerView.isVisible = false
        binding.albumsStatusText.isVisible = true
        binding.albumsStatusText.text = statusText
    }
}