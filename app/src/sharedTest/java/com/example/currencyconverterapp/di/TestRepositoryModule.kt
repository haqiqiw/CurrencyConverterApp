package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.repository.ConvertRepository
import com.example.currencyconverterapp.data.repository.FakeConvertRepository
import com.example.currencyconverterapp.data.repository.FakeRatesRepository
import com.example.currencyconverterapp.data.repository.RatesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule {

    @Binds
    abstract fun bindConvertRepository(
        convertRepository: FakeConvertRepository
    ): ConvertRepository

    @Binds
    abstract fun bindRatesRepository(
        ratesRepository: FakeRatesRepository
    ): RatesRepository
}
