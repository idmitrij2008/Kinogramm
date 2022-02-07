package com.example.kinogramm.view.catalog

import com.example.kinogramm.domain.Film

// TODO: delete?
data class FilmWrapper(private val film: Film, val isLastClicked: Boolean) {
    val id get() = film.id
    val title get() = film.title
    val posterResId get() = film.posterPath
    val description get() = film.overview
}