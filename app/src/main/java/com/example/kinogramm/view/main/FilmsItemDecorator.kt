package com.example.kinogramm.view.main

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.dpToPixel

class FilmsItemDecorator(context: Context) : RecyclerView.ItemDecoration() {
    private val offsetPx = context.dpToPixel(OFFSET_DP).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutParams = view.layoutParams

        // for GridLayoutManager
        if (layoutParams is GridLayoutManager.LayoutParams) {
            if (layoutParams.spanIndex % 2 == 0) {
                outRect.top = offsetPx
                outRect.left = offsetPx
                outRect.right = (offsetPx / 2)
            } else {
                outRect.top = offsetPx
                outRect.right = offsetPx
                outRect.left = (offsetPx / 2)
            }

            // bottom offset for last and pre-last views
            if (parent.getChildAdapterPosition(view) == state.itemCount - 1
                || parent.getChildAdapterPosition(view) == state.itemCount - 2
            ) {
                outRect.bottom = offsetPx
            }

        } else {
            // for LinearLayoutManager
            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.right = offsetPx
            }
            outRect.left = offsetPx
        }
    }

    companion object {
        private const val OFFSET_DP = 8f
    }
}