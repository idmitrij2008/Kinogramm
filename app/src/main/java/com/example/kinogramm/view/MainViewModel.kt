package com.example.kinogramm.view

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    var lastClickedFilmId: Int = 0
}