package com.muratozturk.openai_dall_e_2.network

sealed class ResultData<out T> {
    object Loading: ResultData<Nothing>()
    data class Success<out T>(val data: T? = null): ResultData<T>()
    data class Failed(val message: String? = null): ResultData<Nothing>()
}