package com.example.kinogramm.view.exit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ExitViewModel @Inject constructor() : ViewModel() {
    private val _exit = MutableLiveData<Unit>()
    val exit: LiveData<Unit>
        get() = _exit

    fun exitApp() {
        _exit.value = Unit
    }
}