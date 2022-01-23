package com.example.kinogramm.data

import com.example.kinogramm.data.db.UpdatedFilmDbModel
import com.example.kinogramm.domain.Film

class FilmMapper {
    fun mapModelToEntity(model: FilmModel) = Film(
        id = model.id,
        title = model.title,
        overview = model.overview,
        posterPath = model.posterPath,
        isLiked = model.isLiked
    )

    fun mapEntityToModel(film: Film) = FilmModel(
        id = film.id,
        title = film.title,
        overview = film.overview,
        posterPath = film.posterPath,
        isLiked = film.isLiked
    )

    fun mapListModelToListEntity(list: List<FilmModel>) =
        list.map { mapModelToEntity(it) }

    fun mapListEntityToModel(list: List<Film>): List<FilmModel> =
        list.map { mapEntityToModel(it) }

    fun mapListEntityToUpdDbModel(list: List<Film>): List<UpdatedFilmDbModel> =
        list.map { mapEntityToUpdDbModel(it) }

    private fun mapEntityToUpdDbModel(film: Film) = UpdatedFilmDbModel(
        id = film.id,
        title = film.title,
        overview = film.overview,
        posterPath = film.posterPath
    )
}