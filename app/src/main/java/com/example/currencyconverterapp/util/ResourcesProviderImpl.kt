package com.example.currencyconverterapp.util

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourcesProviderImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ResourcesProvider {

    override fun getString(@StringRes stringResId: Int): String {
        return appContext.resources.getString(stringResId)
    }

    override fun getStringArray(@ArrayRes arrayResId: Int): Array<out String> {
        return appContext.resources.getStringArray(arrayResId)
    }
}
