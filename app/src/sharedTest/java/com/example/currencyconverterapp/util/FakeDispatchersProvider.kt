package com.example.currencyconverterapp.util

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FakeDispatchersProvider @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher
) : DispatchersProvider {
    override val main: CoroutineDispatcher
        get() = coroutineDispatcher
    override val io: CoroutineDispatcher
        get() = coroutineDispatcher
    override val default: CoroutineDispatcher
        get() = coroutineDispatcher
    override val unconfined: CoroutineDispatcher
        get() = coroutineDispatcher
}
