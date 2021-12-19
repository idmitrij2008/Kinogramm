package com.example.kinogramm.view.details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.kinogramm.R
import com.example.kinogramm.data.FavouriteFilmsDataSource
import com.example.kinogramm.data.FilmDataSource
import com.example.kinogramm.data.FilmsCommentsDataSource
import com.example.kinogramm.databinding.FragmentFilmDetailsBinding
import com.example.kinogramm.domain.Film

private const val FILM_ID = "filmId"

class FilmDetailsFragment : Fragment() {

    companion object {
        const val NAME = "FilmDetailsFragment"

        fun newInstance(filmId: Int) =
            FilmDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(FILM_ID, filmId)
                }
            }
    }

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var film: Film

    private val isFavourite get() = film.id in FavouriteFilmsDataSource.likedFilmsIds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            film = FilmDataSource.films.find { it.id == args.getInt(FILM_ID) }
                ?: throw RuntimeException("Film with id ${args.getInt(FILM_ID)} doesn't exist.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmToViews()
        setButtons()
        setCommentEditText()
        binding.mainAppbar?.updateLayoutParams {
            height = 2 * view.resources.displayMetrics.heightPixels / 4
        }
    }

    private fun setButtons() {
        updateLikeButtonColor()

        val likeOnClickListener = View.OnClickListener {
            if (!isFavourite) {
                FavouriteFilmsDataSource.likedFilmsIds.add(film.id)
            } else FavouriteFilmsDataSource.likedFilmsIds.remove(film.id)

            updateLikeButtonColor()
        }

        binding.run {
            likeFab?.setOnClickListener(likeOnClickListener)
            likeButton?.setOnClickListener(likeOnClickListener)

            addCommentButton?.setOnClickListener {
                description.visibility = View.GONE
                userComment?.visibility = View.VISIBLE
            }
        }
    }

    private fun updateLikeButtonColor() {
        val newColor = if (isFavourite) {
            R.color.red_heart
        } else R.color.gray_icon_alpha_50

        binding.likeButton?.imageTintList =
            ColorStateList.valueOf(requireContext().getColor(newColor))

        binding.likeFab?.imageTintList = ColorStateList.valueOf(requireContext().getColor(newColor))
    }

    private fun setFilmToViews() {
        binding.run {
            detailsToolbar?.title = film.title
            poster.setImageResource(film.posterResId)
            description.text = film.description
        }
    }

    private fun setCommentEditText() {
        binding.userComment?.setOnEditorActionListener { commentEditText, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
                commentEditText.text.toString().let { comment ->
                    if (comment.isBlank()) {
                        FilmsCommentsDataSource.filmIdToCommentMap.remove(film.id)
                    } else {
                        FilmsCommentsDataSource.filmIdToCommentMap.put(film.id, comment)
                    }
                }
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
            requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}