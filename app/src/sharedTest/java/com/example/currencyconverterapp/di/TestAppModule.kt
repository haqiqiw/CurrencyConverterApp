package com.example.currencyconverterapp.di

import android.content.Context
import com.example.currencyconverterapp.util.DispatchersProvider
import com.example.currencyconverterapp.util.FakeDispatchersProvider
import com.example.currencyconverterapp.util.FakeResourcesProvider
import com.example.currencyconverterapp.util.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {

    @Singleton
    @Provides
    fun provideDispatchers(): DispatchersProvider {
        return FakeDispatchersProvider(Dispatchers.Main)
    }

    @Singleton
    @Provides
    fun provideResources(@ApplicationContext appContext: Context): ResourcesProvider {
        return FakeResourcesProvider()
    }
}
