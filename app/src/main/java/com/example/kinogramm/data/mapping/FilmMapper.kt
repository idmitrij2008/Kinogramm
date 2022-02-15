package com.example.kinogramm.data.mapping

import com.example.kinogramm.data.models.FilmModel
import com.example.kinogramm.domain.Film
import javax.inject.Inject

class FilmMapper @Inject constructor() {
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