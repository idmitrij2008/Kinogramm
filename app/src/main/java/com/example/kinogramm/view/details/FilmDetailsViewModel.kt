package com.example.kinogramm.view.details

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.Injection
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetFilmUseCase
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

private const val TAG = "FilmDetailsViewModel"

@ExperimentalPagingApi
class FilmDetailsViewModel(
    application: Application,
    filmId: Int
) : ViewModel() {
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

    private var isScheduleDateSet = false
    private var isScheduleTimeSet = false

    private val _filmAlreadyScheduledNotification = MutableLiveData<Unit>(null)
    val filmAlreadyScheduledNotification: LiveData<Unit>
        get() = _filmAlreadyScheduledNotification

    private val _showDatePicker = MutableLiveData<Unit>(null)
    val showDatePicker: LiveData<Unit>
        get() = _showDatePicker

    private val _showTimePicker = MutableLiveData<Unit>(null)
    val showTimePicker: LiveData<Unit>
        get() = _showTimePicker

    val onDateSetListener: DatePickerDialog.OnDateSetListener by lazy {
        DatePickerDialog.OnDateSetListener { _, y, m, d ->
            year = y
            month = m
            day = d

            isScheduleDateSet = true
            _showTimePicker.value = Unit
        }
    }

    val onTimeSetListener: TimePickerDialog.OnTimeSetListener by lazy {
        TimePickerDialog.OnTimeSetListener { _, h, m ->
            hour = h
            minute = m

            isScheduleTimeSet = true
        }
    }

    private val c: Calendar = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
        private set
    var month = c.get(Calendar.MONTH)
        private set
    var day = c.get(Calendar.DAY_OF_MONTH)
        private set
    var hour = c.get(Calendar.HOUR_OF_DAY)
        private set
    var minute = c.get(Calendar.MINUTE)
        private set

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

    fun scheduleClicked() {
        if (!isScheduleDateSet) {
            _showDatePicker.value = Unit
        } else if (!isScheduleTimeSet) {
            _showTimePicker.value = Unit
        } else {
            _filmAlreadyScheduledNotification.value = Unit
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