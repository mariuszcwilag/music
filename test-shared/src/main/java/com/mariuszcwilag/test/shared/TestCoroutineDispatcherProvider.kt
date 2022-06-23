package com.mariuszcwilag.test.shared

import com.mariuszcwilag.core.domain.albums.utils.CoroutineDispatcherProvider
import io.mockk.every
import io.mockk.mockk

fun TestCoroutineRule.getTestCoroutineDispatcherProvider() = mockk<CoroutineDispatcherProvider> {
    every { main } returns testCoroutineDispatcher
    every { io } returns testCoroutineDispatcher
    every { default } returns testCoroutineDispatcher
}