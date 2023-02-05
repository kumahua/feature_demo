package com.example.mvvmdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.network.ApiClient
import com.example.mvvmdemo.network.Character
import com.example.mvvmdemo.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository
                    = Repository(ApiClient.apiService) ) : ViewModel() {

    private var _characterLiveData = MutableLiveData<ScreenState<List<Character>?>>()
    val characterLiveData: LiveData<ScreenState<List<Character>?>>
        get() = _characterLiveData

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {
        val client = repository.getCharacters("1")
        //postValue用於更新 LiveData，可在後台thread調用，其内部會切換到main thread調用
        _characterLiveData.postValue(ScreenState.Loading(null))
        client.enqueue(object : Callback<CharacterResponse>{
             /**
             * 網路請求成功返回，會回調该方法（無論status code是不是200）
             */
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if(response.isSuccessful) { //http status 是 200+
                    //這裡擔心response.body()可能會為null(還沒有測到過這種情況)，所以做了一下這種情况的處理，
                    _characterLiveData.postValue(ScreenState.Success(response.body()?.result))
                } else {
                    _characterLiveData.postValue(ScreenState.Error(response.code().toString(), null))
                }
            }

            /**
             * 在網路請求中發生異常，會回調該方法
             **/
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                _characterLiveData.postValue(ScreenState.Error(t.message.toString(), null))
            }

        })
    }
}