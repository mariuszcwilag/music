package com.mariuszcwilag.core.domain.albums.utils

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Returns a [PagingData] containing the result of applying the given [transform] to each element, as it is loaded.
 * @see PagingData.map
 */
fun <T : Any, R : Any> PagingData<T>.mapWithContext(
    context: CoroutineContext,
    transform: suspend (T) -> R,
): PagingData<R> =
    this.map {
        withContext(context) {
            transform(it)
        }
    }