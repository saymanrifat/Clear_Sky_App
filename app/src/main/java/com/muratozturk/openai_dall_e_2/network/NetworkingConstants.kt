package com.muratozturk.openai_dall_e_2.network

object NetworkingConstants {
    const val BASE_URL = "https://api.weatherapi.com/v1/"
    // example : https://api.weatherapi.com/v1/forecast.json?key=81d2f00f32eb4da4b4e45914210708%20&days=10&aqi=yes&alerts=yes&q=amsterdam
    const val GET_WEATHER = "forecast.json?key=81d2f00f32eb4da4b4e45914210708%20&days=10&aqi=yes&alerts=yes"

    const val DALL_E_BASE_URL = "https://api.openai.com/v1/"
    const val TOKEN = "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"
    const val GENERATE_IMAGE = "images/generations"
    const val SIZE_256 = "256x256"
    const val SIZE_512 = "512x512"
    const val SIZE_1024 = "1024x1024"
}