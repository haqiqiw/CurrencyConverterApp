package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.repository.ConvertRepository
import com.example.currencyconverterapp.data.repository.ConvertRepositoryImpl
import com.example.currencyconverterapp.data.repository.RatesRepository
import com.example.currencyconverterapp.data.repository.RatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindConvertRepository(
        convertRepositoryImpl: ConvertRepositoryImpl
    ): ConvertRepository

    @Binds
    abstract fun bindRatesRepository(
        ratesRepositoryImpl: RatesRepositoryImpl
    ): RatesRepository
}
