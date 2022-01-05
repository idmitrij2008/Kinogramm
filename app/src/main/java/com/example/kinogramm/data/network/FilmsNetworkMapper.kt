package com.example.kinogramm.data.network

import com.example.kinogramm.domain.Film

class FilmsNetworkMapper {

    fun mapNetworkModelToEntity(model: FilmApiModel) = Film(
        model.id,
        model.title,
        model.overview,
        model.posterPath
    )

    fun mapListNetworkModelToListEntity(list: List<FilmApiModel>) =
        list.map { mapNetworkModelToEntity(it) }
}