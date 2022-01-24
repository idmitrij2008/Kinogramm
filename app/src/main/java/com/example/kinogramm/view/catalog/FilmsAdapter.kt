package com.example.kinogramm.view.catalog

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.kinogramm.databinding.FilmListItemBinding
import com.example.kinogramm.databinding.FilmListItemLandBinding
import com.example.kinogramm.domain.Film

class FilmsAdapter(private val viewModel: FilmsCatalogViewModel) :
    PagingDataAdapter<Film, FilmsAdapter.FilmViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
                oldItem == newItem
        }
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
        val currentFilm = getItem(position)

        if (holder.binding is FilmListItemBinding) {
            holder.binding.run {
                film = currentFilm
                viewModel = this@FilmsAdapter.viewModel
            }
        } else if (holder.binding is FilmListItemLandBinding) {
            holder.binding.run {
                film = currentFilm
                viewModel = this@FilmsAdapter.viewModel
            }
        }
    }
}