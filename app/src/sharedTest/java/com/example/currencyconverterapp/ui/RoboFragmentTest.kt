package com.example.currencyconverterapp.ui

import android.os.Build
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    instrumentedPackages = ["androidx.loader.content"],
    sdk = [Build.VERSION_CODES.P],
    application = HiltTestApplication::class,
    manifest = Config.DEFAULT_MANIFEST_NAME
)
abstract class RoboFragmentTest
