package com.example.kinogramm.data.db

import com.example.kinogramm.domain.Film

class FilmsDbMapper {

    fun mapEntityToDbModel(film: Film) = FilmDbModel(
        id = film.id,
        title = film.title,
        overview = film.overview,
        posterPath = film.posterPath,
        isLiked = film.isLiked
    )

    fun mapDbModelToEntity(dbModel: FilmDbModel) = Film(
        id = dbModel.id,
        title = dbModel.title,
        overview = dbModel.overview,
        posterPath = dbModel.posterPath,
        isLiked = dbModel.isLiked
    )

    fun mapListDbModelToListEntity(list: List<FilmDbModel>) =
        list.map { mapDbModelToEntity(it) }

    fun mapListEntityToDbModel(list: List<Film>): List<FilmDbModel> =
        list.map { mapEntityToDbModel(it) }

    fun mapListEntityToUpdDbModel(list: List<Film>): List<UpdatedFilmDbModel> =
        list.map { mapEntityToUpdDbModel(it) }

    private fun mapEntityToUpdDbModel(film: Film) = UpdatedFilmDbModel(
        id = film.id,
        title = film.title,
        overview = film.overview,
        posterPath = film.posterPath
    )
}