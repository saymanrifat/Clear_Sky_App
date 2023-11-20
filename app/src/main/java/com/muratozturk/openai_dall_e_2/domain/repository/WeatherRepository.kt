package com.muratozturk.openai_dall_e_2.domain.repository

import com.muratozturk.openai_dall_e_2.data.model.WeatherModel
import com.muratozturk.openai_dall_e_2.network.WeatherApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApiService: WeatherApiService) {
    suspend fun getWeatherData(city:String): WeatherModel? {
        return weatherApiService.getWeatherData(city)
    }
}