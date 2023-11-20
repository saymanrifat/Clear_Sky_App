package com.muratozturk.openai_dall_e_2.di

import com.muratozturk.openai_dall_e_2.domain.repository.WeatherRepository
import com.muratozturk.openai_dall_e_2.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(weatherApiService: WeatherApiService): WeatherRepository {
        return WeatherRepository(weatherApiService)
    }
}