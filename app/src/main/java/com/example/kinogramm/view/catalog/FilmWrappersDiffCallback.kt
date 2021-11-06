package com.example.kinogramm.view.catalog

import androidx.recyclerview.widget.DiffUtil

class FilmWrappersDiffCallback(
    private val oldList: List<FilmWrapper>,
    private val newList: List<FilmWrapper>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]

        return old.id == new.id && old.isLastClicked == new.isLastClicked
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}