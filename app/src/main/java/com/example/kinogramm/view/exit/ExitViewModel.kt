package com.example.kinogramm.view.exit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExitViewModel : ViewModel() {
    private val _exit = MutableLiveData<Unit>()
    val exit: LiveData<Unit>
        get() = _exit

    fun exitApp() {
        _exit.value = Unit
    }
}