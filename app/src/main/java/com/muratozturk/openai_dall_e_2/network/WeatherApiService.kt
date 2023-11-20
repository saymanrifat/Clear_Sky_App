package com.muratozturk.openai_dall_e_2.network

import com.muratozturk.openai_dall_e_2.data.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET(NetworkingConstants.GET_WEATHER)
    suspend fun getWeatherData(
        @Query("q") city: String
    ): WeatherModel?
}