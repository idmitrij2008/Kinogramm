package com.example.kinogramm.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Context.dpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun TextView.hideKeyBoard() {
    clearFocus()
    val imm =
        context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showShortToast(string: String?) {
    Toast.makeText(this, string ?: "", Toast.LENGTH_SHORT).show()
}