package com.example.kinogramm.view.catalog

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.kinogramm.databinding.FilmListItemBinding
import com.example.kinogramm.databinding.FilmListItemLandBinding
import com.example.kinogramm.domain.Film

class FilmsAdapter(private val viewModel: FilmsCatalogViewModel) :
    RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    var films = listOf<Film>()
        set(value) {
            val diffCallback = FilmWrappersDiffCallback(films, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    class FilmViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = when (parent.resources.configuration.orientation) {
            ORIENTATION_LANDSCAPE -> FilmListItemLandBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> FilmListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }

        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val wrappedFilm = films[position]

        if (holder.binding is FilmListItemBinding) {
            holder.binding.run {
                film = wrappedFilm
                viewModel = this@FilmsAdapter.viewModel
            }
        } else if (holder.binding is FilmListItemLandBinding) {
            holder.binding.run {
                film = wrappedFilm
                viewModel = this@FilmsAdapter.viewModel
            }
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }
}