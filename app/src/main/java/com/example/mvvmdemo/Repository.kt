package com.example.mvvmdemo

import com.example.mvvmdemo.network.ApiService

class Repository(private val apiService: ApiService) {
    fun getCharacters(page: String) = apiService.fetchCharacter(page)
}