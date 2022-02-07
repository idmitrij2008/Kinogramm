package com.example.kinogramm.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kinogramm.util.Constants.IMAGE_BASE_URL

@BindingAdapter("imageUri")
fun ImageView.bindImageUri(uri: String?) {
    uri?.let { Glide.with(context).load(IMAGE_BASE_URL + it).into(this) }
}