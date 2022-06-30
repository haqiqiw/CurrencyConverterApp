package com.example.currencyconverterapp.di

import android.content.Context
import com.example.currencyconverterapp.data.remote.service.CurrencyService
import com.example.currencyconverterapp.util.DispatchersProvider
import com.example.currencyconverterapp.util.ResourcesProvider
import com.example.currencyconverterapp.util.ResourcesProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.exchangerate.host"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyService::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatchers(currencyApi: CurrencyService): DispatchersProvider {
        return object : DispatchersProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined
        }
    }

    @Singleton
    @Provides
    fun provideResources(@ApplicationContext appContext: Context): ResourcesProvider {
        return ResourcesProviderImpl(appContext)
    }
}
