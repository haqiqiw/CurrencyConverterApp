package com.example.currencyconverterapp.util

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes stringResId: Int): String

    fun getStringArray(@ArrayRes arrayResId: Int): Array<out String>
}
