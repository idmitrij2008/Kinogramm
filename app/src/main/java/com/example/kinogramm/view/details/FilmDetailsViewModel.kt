package com.example.kinogramm.view.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.Injection
import com.example.kinogramm.domain.usecases.GetFilmUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class FilmDetailsViewModel(application: Application, filmId: Int) :
    AndroidViewModel(application) {

    private val repository = Injection.provideFilmsRepository(application)
    private val likeFilmUseCase = LikeFilmUseCase(repository)
    private val getFilmUseCase = GetFilmUseCase(repository)

    val filmLD = getFilmUseCase.getFilmLD(filmId)

    fun likeClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            filmLD.value?.let { likeFilmUseCase.changeIsLikedState(it) }
        }
    }

    private val _addCommentClicked = MutableLiveData(false)

    val addCommentClicked: LiveData<Boolean>
        get() = _addCommentClicked

    fun addingComment(isAdding: Boolean) {
        _addCommentClicked.value = isAdding
    }

    fun removeComment() {
        // TODO
    }

    fun updateComment(comment: String) {
        // TODO
    }
}