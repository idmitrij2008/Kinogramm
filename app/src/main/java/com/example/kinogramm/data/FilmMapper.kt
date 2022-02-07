package com.example.kinogramm.data

import com.example.kinogramm.domain.Film

class FilmMapper {
    fun mapModelToEntity(model: FilmModel) = Film(
        id = model.filmId,
        remoteId = model.remoteId,
        title = model.title,
        overview = model.overview,
        posterPath = model.posterPath
    )

    fun mapListModelToListEntity(list: List<FilmModel>) =
        list.map { mapModelToEntity(it) }

}