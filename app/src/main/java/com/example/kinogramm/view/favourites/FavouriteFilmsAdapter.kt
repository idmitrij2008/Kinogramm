package com.example.kinogramm.view.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.databinding.FavouriteFilmListItemBinding
import com.example.kinogramm.domain.Film

class FavouriteFilmsAdapter :
    RecyclerView.Adapter<FavouriteFilmsAdapter.FavouriteFilmViewHolder>() {

    var films = listOf<Film>()
        set(value) {
            val diffCallback = FavouriteFilmsDiffCallback(films, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    class FavouriteFilmViewHolder(val binding: FavouriteFilmListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteFilmViewHolder {
        val binding =
            FavouriteFilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavouriteFilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteFilmViewHolder, position: Int) {
        holder.binding.film = films[position]
    }

    override fun getItemCount(): Int {
        return films.size
    }
}
