package com.example.kinogramm.view.main

import com.example.kinogramm.domain.Film

data class FilmWrapper(private val film: Film, val isLastClicked: Boolean) {
    val id get() = film.id
    val title get() = film.title
    val posterResId get() = film.posterResId
    val description get() = film.description
}