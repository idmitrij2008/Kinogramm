package com.example.kinogramm.view.favourites

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.data.FilmDataSource
import com.example.kinogramm.databinding.ActivityFavouriteFilmsBinding
import com.example.kinogramm.domain.Film
import java.util.*

private const val TAG = "FavouriteFilmsActivity"

class FavouriteFilmsActivity : AppCompatActivity() {

    companion object {
        private const val FAVOURITE_FILMS = "favouriteFilms"
        const val EXTRA_FAVOURITE_IDS = "favouriteIds"
        fun newIntent(context: Context, favouriteFilmIds: IntArray) =
            Intent(context, FavouriteFilmsActivity::class.java).apply {
                putExtra(EXTRA_FAVOURITE_IDS, favouriteFilmIds)
            }
    }

    private lateinit var binding: ActivityFavouriteFilmsBinding
    private lateinit var favouriteFilms: MutableList<Film>
    private lateinit var favouriteFilmsAdapter: FavouriteFilmsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteFilmsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()

        restoreState(savedInstanceState)
        setUpRecyclerView()
        updateResultState()
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            favouriteFilms =
                savedInstanceState.getParcelableArrayList<Film>(FAVOURITE_FILMS)?.toMutableList()
                    ?: mutableListOf()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(FAVOURITE_FILMS, favouriteFilms as ArrayList)
    }

    private fun setUpRecyclerView() {
        binding.favouriteFilmsRv.run {
            favouriteFilmsAdapter = FavouriteFilmsAdapter()
            updateFavouriteFilmsAdapter()
            adapter = favouriteFilmsAdapter

            addItemDecoration(
                DividerItemDecoration(
                    this@FavouriteFilmsActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            setupSwipeListener(this)
        }
    }

    private fun setupSwipeListener(favouriteFilmsRV: RecyclerView) {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val film = favouriteFilmsAdapter.films[viewHolder.adapterPosition]
                favouriteFilms.remove(film)
                updateFavouriteFilmsAdapter()
                updateResultState()
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(favouriteFilmsRV)
    }

    private fun parseIntent() {
        intent.getIntArrayExtra(EXTRA_FAVOURITE_IDS)?.let { extraFavouriteFilmIds ->
            favouriteFilms = FilmDataSource.films.filter { it.id in extraFavouriteFilmIds }
                .toMutableList()
        } ?: run {
            Log.w(TAG, "No favourite films presented.")
        }
    }

    private fun updateFavouriteFilmsAdapter() {
        favouriteFilmsAdapter.films = favouriteFilms.toList()
    }

    private fun updateResultState() {
        Intent()
            .putExtra(EXTRA_FAVOURITE_IDS, favouriteFilms.map { it.id }.toIntArray())
            .also { intent ->
                setResult(Activity.RESULT_OK, intent)
            }
    }
}