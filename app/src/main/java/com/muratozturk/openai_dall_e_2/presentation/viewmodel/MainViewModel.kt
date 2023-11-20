package com.muratozturk.openai_dall_e_2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.muratozturk.openai_dall_e_2.data.model.WeatherModel
import com.muratozturk.openai_dall_e_2.network.ResultData
import com.muratozturk.openai_dall_e_2.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: WeatherUseCase) : ViewModel() {
    fun getWeatherData(city: String): LiveData<ResultData<WeatherModel>> {
        return useCase.getWeatherData(city).asLiveData()
    }
}