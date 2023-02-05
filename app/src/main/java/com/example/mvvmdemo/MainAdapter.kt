package com.example.mvvmdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mvvmdemo.databinding.RvItemBinding
import com.example.mvvmdemo.network.Character

class MainAdapter(val charactersList: List<Character>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    class MainViewHolder(binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val name = binding.name
        val image = binding.image

        fun bindData(character: Character){
            name.text = character.name
            image.load(character.image){
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(charactersList[position])
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }
}