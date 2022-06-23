package com.mariuszcwilag.core.data.utils

import androidx.paging.PagingConfig

object PagingUtils {

    private const val PAGE_SIZE_DEFAULT = 30

    fun buildConfig() = PagingConfig(
        pageSize = PAGE_SIZE_DEFAULT,
        initialLoadSize = PAGE_SIZE_DEFAULT,

        )
}