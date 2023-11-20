package com.weather.forecast.clearsky.model

import com.muratozturk.openai_dall_e_2.data.model.Day
import com.muratozturk.openai_dall_e_2.data.model.Hour

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)