package com.example.mvvmdemo.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    //Moshi為Json的encode 、decode 框架
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //通過lazy創建一個Retrofit的實例，這樣它就可以在需要傳遞BASE_URL和上面創建的moshi變量時進行初始化
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("character")
    fun fetchCharacter(@Query("page") page: String): Call<CharacterResponse>
}