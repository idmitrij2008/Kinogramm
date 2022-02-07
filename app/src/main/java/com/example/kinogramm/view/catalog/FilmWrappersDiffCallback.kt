package com.example.kinogramm.view.catalog

import androidx.recyclerview.widget.DiffUtil
import com.example.kinogramm.domain.Film

class FilmWrappersDiffCallback(
    private val oldList: List<Film>,
    private val newList: List<Film>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}