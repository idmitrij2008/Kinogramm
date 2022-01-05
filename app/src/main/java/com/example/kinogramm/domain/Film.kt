package com.example.kinogramm.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val isLiked: Boolean = false
) : Parcelable
