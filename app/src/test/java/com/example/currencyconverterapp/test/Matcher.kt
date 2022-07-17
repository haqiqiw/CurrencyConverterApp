package com.example.currencyconverterapp.test

import io.mockk.MockKMatcherScope
import kotlin.reflect.KClass

inline fun <reified T : Any, R : T> MockKMatcherScope.ofTypeMatch(
    cls: KClass<R>,
    noinline matcher: (T) -> Unit
) = and(
    ofType<T, R>(cls),
    match {
        matcher(it)
        true
    }
)
