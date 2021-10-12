package com.example.kinogramm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kinogramm.databinding.ActivityFilmDetailsBinding


private const val TAG = "FilmDetailsActivity"

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmDetailsBinding
    private lateinit var film: Film
    private var isLiked = false
    private var userComment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntent()
        setFilmToViews()
        setButtons()
        setCommentEditText()

        updateResultState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FILM_LIKED, isLiked)
        outState.putString(USER_COMMENT, userComment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isLiked = savedInstanceState.getBoolean(FILM_LIKED)
        userComment = savedInstanceState.getString(USER_COMMENT) ?: ""
        updateResultState()
    }

    private fun setButtons() {
        binding.run {
            likeButton.setOnClickListener {
                isLiked = !isLiked
                updateResultState()
            }

            addCommentButton.setOnClickListener {
                description.visibility = View.GONE
                userComment.visibility = View.VISIBLE
            }
        }
    }

    private fun setCommentEditText() {
        binding.userComment.setOnEditorActionListener { commentEditText, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
                userComment = commentEditText.text.toString()
                updateResultState()
                commentEditText.hideKeyBoard()
                commentEditText.visibility = View.GONE
                binding.description.visibility = View.VISIBLE
                true
            } else false
        }
    }

    private fun TextView.hideKeyBoard() {
        clearFocus()
        val imm =
            this@FilmDetailsActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun updateResultState() {
        updateLikeButtonColor()

        Intent()
            .putExtra(MainActivity.EXTRA_LIKED, isLiked)
            .putExtra(MainActivity.EXTRA_USER_COMMENT, userComment)
            .also { data ->
                setResult(Activity.RESULT_OK, data)
            }
    }

    private fun updateLikeButtonColor() {
        val newColor = if (isLiked) {
            R.color.red_heart
        } else R.color.gray_icon_alpha_50

        binding.likeButton.imageTintList =
            ColorStateList.valueOf(this.getColor(newColor))
    }

    private fun setFilmToViews() {
        binding.run {
            poster.setImageResource(film.posterResId)
            description.text = film.description
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_FILM_ID)) throw RuntimeException("Param filmId is not presented.")

        val filmId = intent.getIntExtra(EXTRA_FILM_ID, 0)
        val foundFilm = FilmDataSource.films.find { it.id == filmId }
        if (foundFilm != null) {
            film = foundFilm
        } else {
            Log.w(TAG, "Film with id = $filmId not found.")
            finish()
        }
    }

    companion object {
        private const val EXTRA_FILM_ID = "filmId"
        private const val FILM_LIKED = "liked"
        private const val USER_COMMENT = "userComment"

        fun newIntent(context: Context, filmId: Int) =
            Intent(context, FilmDetailsActivity::class.java).apply {
                putExtra(EXTRA_FILM_ID, filmId)
            }
    }
}