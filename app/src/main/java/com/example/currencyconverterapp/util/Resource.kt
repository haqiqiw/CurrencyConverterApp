package com.example.currencyconverterapp.util

import com.example.currencyconverterapp.util.Resource.Success

sealed class Resource<out R> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message]"
        }
    }
}

val Resource<*>.isSuccess: Boolean
    get() = this is Success && data != null
