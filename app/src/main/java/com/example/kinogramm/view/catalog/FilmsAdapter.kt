package com.example.kinogramm.view.catalog

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.kinogramm.R
import com.example.kinogramm.databinding.FilmListItemBinding
import com.example.kinogramm.databinding.FilmListItemLandBinding

class FilmsAdapter(private val context: Context) :
    RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    var wrappedFilms = listOf<FilmWrapper>()
        set(value) {
            val diffCallback = FilmWrappersDiffCallback(wrappedFilms, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var detailsOnClick: ((FilmWrapper) -> Unit)? = null

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
        val wrappedFilm = wrappedFilms[position]

        if (holder.binding is FilmListItemBinding) {
            holder.binding.run {
                poster.setImageResource(wrappedFilm.posterResId)
                poster.contentDescription = wrappedFilm.title
                title.text = wrappedFilm.title
                if (wrappedFilm.isLastClicked) title.setTextColor(
                    context.getColor(
                        R.color.text_highlighted
                    )
                )
                details.setOnClickListener {
                    detailsOnClick?.invoke(wrappedFilm)
                }
            }
        } else if (holder.binding is FilmListItemLandBinding) {
            holder.binding.run {
                poster.setImageResource(wrappedFilm.posterResId)
                poster.contentDescription = wrappedFilm.title
                title.text = wrappedFilm.title
                if (wrappedFilm.isLastClicked) title.setTextColor(
                    context.getColor(
                        R.color.text_highlighted
                    )
                )
                details.setOnClickListener {
                    detailsOnClick?.invoke(wrappedFilm)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return wrappedFilms.size
    }
}