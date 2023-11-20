package com.muratozturk.openai_dall_e_2.data.model

import com.weather.forecast.clearsky.model.Alerts
import com.weather.forecast.clearsky.model.Current
import com.weather.forecast.clearsky.model.Forecast
import com.weather.forecast.clearsky.model.Location

data class WeatherModel(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)