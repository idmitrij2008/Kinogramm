package com.example.kinogramm.view.details

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.Injection
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetFilmUseCase
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
class FilmDetailsViewModel(application: Application, filmId: Int) :
    ViewModel() {

    private val repository = Injection.provideFilmsRepository(application)
    private val likeFilmUseCase = LikeFilmUseCase(repository)
    private val getLikedFilmsUseCase = GetLikedFilmsUseCase(repository)
    private val getFilmUseCase = GetFilmUseCase(repository)

    private val likedFilmsObserver: Observer<List<Int>> by lazy {
        Observer { likedIds ->
            _film.value?.let { f ->
                _isLiked.value = f.remoteId in likedIds
            }
        }
    }

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film>
        get() = _film

    private val _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean>
        get() = _isLiked

    private var likedFilms = getLikedFilmsUseCase.getLikedFilms()

    init {
        viewModelScope.launch {
            val film = withContext(Dispatchers.IO) { getFilmUseCase.getFilm(filmId) }
            _film.value = film
            likedFilms.observeForever(likedFilmsObserver)
        }
    }

    override fun onCleared() {
        likedFilms.removeObserver(likedFilmsObserver)
    }

    fun likeClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_isLiked.value == true) {
                _film.value?.let { likeFilmUseCase.unLikeFilm(it) }
            } else if (_isLiked.value == false) {
                _film.value?.let { likeFilmUseCase.likeFilm(it) }
            }
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