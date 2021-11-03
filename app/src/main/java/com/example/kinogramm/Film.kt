package com.example.kinogramm

import androidx.annotation.DrawableRes

data class Film(
    val id: Int,
    val title: String,
    @DrawableRes val posterResId: Int,
    val description: String
)
