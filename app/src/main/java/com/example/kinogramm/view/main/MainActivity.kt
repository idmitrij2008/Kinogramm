package com.example.kinogramm.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.example.kinogramm.R
import com.example.kinogramm.data.FilmDataSource
import com.example.kinogramm.databinding.ActivityMainBinding
import com.example.kinogramm.view.details.FilmDetailsActivity
import com.example.kinogramm.view.favourites.FavouriteFilmsActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LAST_CLICKED_FILM_ID = "lastClickedFilmId"
        private const val FAVOURITE_FILM_IDS = "favouriteFilmIds"
        const val UNKNOWN_FILM_ID = -1
        const val EXTRA_LIKED = "liked"
        const val EXTRA_USER_COMMENT = "userComment"
    }

    private lateinit var binding: ActivityMainBinding

    private var lastClickedFilmId = UNKNOWN_FILM_ID
    private lateinit var favouriteFilmIds: MutableList<Int>

    private val wrappedFilms: List<FilmWrapper>
        get() = FilmDataSource.films.map {
            FilmWrapper(it, it.id == lastClickedFilmId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restoreState(savedInstanceState)
        setupFilmsRecyclerView()
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        lastClickedFilmId = savedInstanceState?.getInt(LAST_CLICKED_FILM_ID) ?: UNKNOWN_FILM_ID
        favouriteFilmIds =
            savedInstanceState?.getIntArray(FAVOURITE_FILM_IDS)?.toMutableList() ?: mutableListOf()
    }

    override fun onBackPressed() {
        ExitDialogFragment().show(supportFragmentManager, null)
    }

    private fun setupFilmsRecyclerView() {
        binding.films.run {

            adapter = FilmsAdapter(this@MainActivity).apply {
                wrappedFilms = this@MainActivity.wrappedFilms

                detailsOnClick = { wrappedFilm ->
                    filmDetailsResultLauncher.launch(
                        FilmDetailsActivity.newIntent(
                            context = this@MainActivity,
                            filmId = wrappedFilm.id,
                            isFavourite = wrappedFilm.id in favouriteFilmIds
                        )
                    )

                    lastClickedFilmId = wrappedFilm.id
                    wrappedFilms = this@MainActivity.wrappedFilms
                }
            }

            addItemDecoration(FilmsItemDecorator())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_share -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Let's use Kinogramm together!")
                }.also { intent ->
                    startActivity(intent)
                }
                true
            }
            R.id.action_show_favourites -> {
                favouriteFilmsResultLauncher.launch(
                    FavouriteFilmsActivity.newIntent(
                        context = this,
                        favouriteFilmIds = favouriteFilmIds.toIntArray()
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_CLICKED_FILM_ID, lastClickedFilmId)
        outState.putIntArray(FAVOURITE_FILM_IDS, favouriteFilmIds.toIntArray())
    }

    private val filmDetailsResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val isLiked = result.data?.getBooleanExtra(EXTRA_LIKED, false) ?: false
                val userComment = result.data?.getStringExtra(EXTRA_USER_COMMENT) ?: ""

                FilmDataSource.films.find { it.id == lastClickedFilmId }?.let { film ->
                    Log.d(TAG, "Film ${film.title} liked = $isLiked")
                    Log.d(TAG, "Film ${film.title} userComment = $userComment")

                    if (isLiked) {
                        addFavouriteFilm(film.id)
                    } else favouriteFilmIds.remove(film.id)
                }
            }
        }

    private val favouriteFilmsResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val filmsIds =
                    result.data?.getIntArrayExtra(FavouriteFilmsActivity.EXTRA_FAVOURITE_IDS)
                        ?.toMutableList()

                filmsIds?.let { favouriteFilmIds = filmsIds }
            }
        }

    private fun addFavouriteFilm(filmId: Int) {
        if (filmId !in favouriteFilmIds) {
            favouriteFilmIds.add(filmId)
        }
    }
}