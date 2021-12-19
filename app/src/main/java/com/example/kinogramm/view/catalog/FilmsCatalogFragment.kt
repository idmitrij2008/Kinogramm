package com.example.kinogramm.view.catalog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.kinogramm.R
import com.example.kinogramm.data.FilmDataSource
import com.example.kinogramm.databinding.FragmentFilmsCatalogBinding
import com.example.kinogramm.view.details.FilmDetailsFragment
import com.example.kinogramm.view.main.FilmsCatalogItemDecorator

private const val UNKNOWN_FILM_ID = -1
private const val LAST_CLICKED_FILM_ID = "lastClickedFilmId"

class FilmsCatalogFragment : Fragment() {

    companion object {
        const val NAME = "FilmsCatalogFragment"

        fun newInstance() = FilmsCatalogFragment()
    }

    private var _binding: FragmentFilmsCatalogBinding? = null
    private val binding get() = _binding!!

    private var lastClickedFilmId = UNKNOWN_FILM_ID

    private val wrappedFilms: List<FilmWrapper>
        get() = FilmDataSource.films.map {
            FilmWrapper(it, it.id == lastClickedFilmId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lastClickedFilmId = savedInstanceState?.getInt(LAST_CLICKED_FILM_ID) ?: UNKNOWN_FILM_ID
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsCatalogBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilmsRecyclerView()
    }

    private fun setupFilmsRecyclerView() {
        binding.catalogRv.adapter = FilmsAdapter(requireContext()).apply {
            wrappedFilms = this@FilmsCatalogFragment.wrappedFilms

            detailsOnClick = { film ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_container,
                        FilmDetailsFragment.newInstance(film.id)
                    )
                    .addToBackStack(FilmDetailsFragment.NAME)
                    .commit()

                lastClickedFilmId = film.id
                wrappedFilms = this@FilmsCatalogFragment.wrappedFilms
            }
        }

        binding.catalogRv.addItemDecoration(FilmsCatalogItemDecorator())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_CLICKED_FILM_ID, lastClickedFilmId)
    }
}