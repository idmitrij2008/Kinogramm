package com.example.kinogramm.util

object Constants {
    const val API_BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "60ae4ec96f6a2966d889bc6d7fe09155"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
    const val QUERY_API_KEY = "api_key"
    const val QUERY_PAGE = "page"

    const val DB_NAME = "films_db"
    const val FILMS_TABLE = "films_table"
    const val REMOTE_KEYS_TABLE = "remote_keys_table"
    const val LIKED_FILMS_TABLE = "liked_films_table"
    const val SCHEDULED_FILMS_TABLE = "scheduled_films_table"

    const val SHARED_PREFS_NAME = "kinogramm_prefs"
    const val FIREBASE_MESSAGING_TOKEN_NAME = "messaging_token"

    const val ACTION_SCHEDULED_FILM = "com.example.kinogramm.action.SCHEDULED_FILM"
    const val EXTRA_FILM_REMOTE_ID = "film_remote_id"
    const val EXTRA_FILM_TITLE = "film_title"
}