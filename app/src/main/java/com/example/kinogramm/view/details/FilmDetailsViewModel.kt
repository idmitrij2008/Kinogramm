package com.example.kinogramm.view.details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.FilmRemoteIdQualifier
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.AddScheduledFilmUseCase
import com.example.kinogramm.domain.usecases.GetFilmUseCase
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

private const val TAG = "FilmDetailsViewModel"

@ExperimentalPagingApi
class FilmDetailsViewModel @Inject constructor(
    private val likeFilmUseCase: LikeFilmUseCase,
    getLikedFilmsUseCase: GetLikedFilmsUseCase,
    getFilmUseCase: GetFilmUseCase,
    private val addScheduledFilmUseCase: AddScheduledFilmUseCase,
    @FilmRemoteIdQualifier private val filmRemoteId: Int
) : ViewModel() {
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

    private val _scheduleFilm = MutableLiveData<Unit>(null)
    val scheduleFilm: LiveData<Unit>
        get() = _scheduleFilm

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

            _scheduleFilm.value = Unit

            film.value?.let { f ->
                Log.d(TAG, "Scheduling film with remoteId: ${f.remoteId}")
                addScheduledFilmUseCase.addScheduledFilm(f.remoteId)
            }
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

    val scheduledTimeMillis: Long
        get() = Calendar.getInstance().apply {
            set(year, month, day, hour, minute)
        }.timeInMillis

    init {
        getFilmUseCase.getFilm(filmRemoteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ film ->
                _film.value = film
                observeLikedFilms()
            }, { e ->
                Log.e(TAG, "${e.message}")
            })
    }

    private fun observeLikedFilms() {
        likedFilms
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { likedIds ->
                _film.value?.let { f ->
                    _isLiked.value = f.remoteId in likedIds
                }
            }
    }

    fun likeClicked() {
        if (_isLiked.value == true) {
            _film.value?.let { f ->
                likeFilmUseCase.unLikeFilm(f)
            }
        } else if (_isLiked.value == false) {
            _film.value?.let { f ->
                likeFilmUseCase.likeFilm(f)
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