package com.example.mvvmdemo

sealed class ScreenState<T>(val data:T ?= null, val message: String ?= null) {
    //used once the call was order response was successful
    class Success<T>(data: T) : ScreenState<T>(data)

    class Loading<T>(data: T? = null) : ScreenState<T>(data)

    class Error<T>(message: String, data: T? = null) : ScreenState<T>(data,message)
}