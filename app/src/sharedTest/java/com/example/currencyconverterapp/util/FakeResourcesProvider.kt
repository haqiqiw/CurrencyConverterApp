package com.example.currencyconverterapp.util

import javax.inject.Inject

class FakeResourcesProvider @Inject constructor() : ResourcesProvider {

    private val stringMap: MutableMap<Int, String> = mutableMapOf()
    private val stringArrayMap: MutableMap<Int, Array<String>> = mutableMapOf()

    fun setString(stringResId: Int, stringResValue: String) {
        stringMap[stringResId] = stringResValue
    }

    fun setStringArray(arrayResId: Int, arrayResValue: Array<String>) {
        stringArrayMap[arrayResId] = arrayResValue
    }

    override fun getString(stringResId: Int): String {
        return stringMap[stringResId].orEmpty()
    }

    override fun getStringArray(arrayResId: Int): Array<out String> {
        return stringArrayMap[arrayResId].orEmpty()
    }
}
