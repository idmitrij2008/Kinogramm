package com.example.kinogramm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.example.kinogramm.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastClickedFilmId = 0

    @ColorRes
    private val highlightedTextColor = R.color.text_highlighted

    @ColorRes
    private val normalTextColor = R.color.text

    private val resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val isLiked = result.data?.getBooleanExtra(EXTRA_LIKED, false) ?: false
            val userComment = result.data?.getStringExtra(EXTRA_USER_COMMENT) ?: ""

            val film = FilmDataSource.films.find { it.id == lastClickedFilmId }
            film?.let {
                Log.d(TAG, "Film ${film.title} liked = $isLiked")
                Log.d(TAG, "Film ${film.title} userComment = $userComment")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFilmsToViews()
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
            else -> super.onOptionsItemSelected(item)
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CHOSEN_FILM_ID, lastClickedFilmId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastClickedFilmId = savedInstanceState.getInt(CHOSEN_FILM_ID)
        setTextColorToFilmTitle(lastClickedFilmId, highlightedTextColor)
    }

    private fun setFilmsToViews() {
        binding.run {

            FilmDataSource.films[0].let { film ->
                poster1.setImageResource(film.posterResId)
                poster1.contentDescription = film.title
                title1.text = film.title

                details1.setOnClickListener {
                    highlightOnlyFilmWithId(film.id)
                    resultLauncher.launch(FilmDetailsActivity.newIntent(this@MainActivity, film.id))
                }
            }
            FilmDataSource.films[1].let { film ->
                poster2.setImageResource(film.posterResId)
                poster2.contentDescription = film.title
                title2.text = film.title

                details2.setOnClickListener {
                    highlightOnlyFilmWithId(film.id)
                    resultLauncher.launch(FilmDetailsActivity.newIntent(this@MainActivity, film.id))
                }
            }
            FilmDataSource.films[2].let { film ->
                poster3.setImageResource(film.posterResId)
                poster3.contentDescription = film.title
                title3.text = film.title

                details3.setOnClickListener {
                    highlightOnlyFilmWithId(film.id)
                    resultLauncher.launch(FilmDetailsActivity.newIntent(this@MainActivity, film.id))
                }
            }
            FilmDataSource.films[3].let { film ->
                poster4.setImageResource(film.posterResId)
                poster4.contentDescription = film.title
                title4.text = film.title

                details4.setOnClickListener {
                    highlightOnlyFilmWithId(film.id)
                    resultLauncher.launch(FilmDetailsActivity.newIntent(this@MainActivity, film.id))
                }
            }
        }
    }

    private fun highlightOnlyFilmWithId(filmId: Int) {
        setTextColorToFilmTitle(filmId, highlightedTextColor)
        setTextColorToFilmTitle(lastClickedFilmId, normalTextColor)
        lastClickedFilmId = filmId
    }

    private fun setTextColorToFilmTitle(filmId: Int, @ColorRes color: Int) {
        binding.run {
            val newColor = this@MainActivity.getColor(color)
            when (filmId) {
                1 -> title1.setTextColor(newColor)
                2 -> title2.setTextColor(newColor)
                3 -> title3.setTextColor(newColor)
                4 -> title4.setTextColor(newColor)
            }
        }
    }

    companion object {
        private const val CHOSEN_FILM_ID = "chosenFilmId"
        const val EXTRA_LIKED = "liked"
        const val EXTRA_USER_COMMENT = "userComment"
    }
}