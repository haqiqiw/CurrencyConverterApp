package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.domain.usecase.FakeGetRatesUseCase
import com.example.currencyconverterapp.domain.usecase.GetRatesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
abstract class TestUseCaseModule {

    @Binds
    abstract fun bindGetRatesUseCase(
        getRatesUseCase: FakeGetRatesUseCase
    ): GetRatesUseCase
}
