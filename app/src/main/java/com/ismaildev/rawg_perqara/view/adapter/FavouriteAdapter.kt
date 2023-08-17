package com.ismaildev.rawg_perqara.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ismaildev.rawg_perqara.R
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.databinding.ItemGameBinding
import com.ismaildev.rawg_perqara.util.myLog

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private var listGame = emptyList<ModelGame>()
    lateinit var context: Context
    fun setData(data: List<ModelGame>, context: Context) {
        listGame = data
        notifyDataSetChanged()
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initData(listGame[position],context)
    }

    override fun getItemCount(): Int {
        return listGame.size
    }

    class ViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initData(gameItem: ModelGame, context: Context) {
            binding.title.text = gameItem.title
            binding.rating.text = gameItem.rating.toString()
            binding.release.text = "Release date "+ gameItem.released
            Glide.with(context).load(gameItem.background).into(binding.imageGame)
            val bundle = Bundle()
            bundle.putInt("id",gameItem.idGame!!)
            binding.container.setOnClickListener {
                it.findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment,bundle)
            }
        }
    }

}