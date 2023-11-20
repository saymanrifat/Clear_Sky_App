package com.muratozturk.openai_dall_e_2.di

import com.muratozturk.openai_dall_e_2.domain.repository.WeatherRepository
import com.muratozturk.openai_dall_e_2.usecase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesDataUseCase(weatherRepository: WeatherRepository): WeatherUseCase {
        return WeatherUseCase(weatherRepository)
    }
}