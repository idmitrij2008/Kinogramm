package com.example.kinogramm.domain

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Int,
    val title: String,
    @DrawableRes val posterResId: Int,
    val description: String
) : Parcelable
