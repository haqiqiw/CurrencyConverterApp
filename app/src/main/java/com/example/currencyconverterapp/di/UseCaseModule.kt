package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.domain.usecase.GetRatesUseCase
import com.example.currencyconverterapp.domain.usecase.GetRatesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetRatesUseCase(
        getRatesUseCaseImpl: GetRatesUseCaseImpl
    ): GetRatesUseCase
}
