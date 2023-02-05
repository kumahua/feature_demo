package com.example.mvvmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.network.ApiClient
import com.example.mvvmdemo.network.Character
import com.example.mvvmdemo.network.CharacterResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

// dev
// t1_child
// t1_child2
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var charactersRv: RecyclerView

    // MainViewModel 只初始化一次，然後我們使用 ViewModelProvider 來初始化 MainViewModel 類
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 透過 observe function 來等待資料的更新，當資料有更新的時候我們就改變我們的畫面
        viewModel.characterLiveData.observe(this) { state ->
            processCharactersResponse(state)
        }
    }

    private fun processCharactersResponse(state: ScreenState<List<Character>?>) {
        val pb = binding.progressBar
        when (state) {
            is ScreenState.Loading -> {
                pb.visibility = View.VISIBLE
            }
            is ScreenState.Success -> {
                pb.visibility = View.GONE
                if (state.data != null) {
                    adapter = MainAdapter(state.data)
                    charactersRv = binding.charactersRv
                    charactersRv.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    charactersRv.adapter = adapter
                }
            }
            is ScreenState.Error -> {
                pb.visibility = View.GONE
                val view = pb.rootView
                Snackbar.make(view,state.message!!,Snackbar.LENGTH_LONG).show()
            }
        }
    }
}