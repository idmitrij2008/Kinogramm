package com.example.kinogramm.view.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kinogramm.R
import com.example.kinogramm.databinding.FragmentFilmsCatalogBinding
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.Result
import com.example.kinogramm.view.main.FilmsCatalogItemDecorator
import com.google.android.material.snackbar.Snackbar

private val TAG = "FilmsCatalogFragment"

class FilmsCatalogFragment : Fragment() {
    private var _binding: FragmentFilmsCatalogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmsCatalogViewModel by viewModels()
    private var filmsAdapter: FilmsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.showDetailsForFilm.observe(this) { film ->
            findNavController().navigate(
                FilmsCatalogFragmentDirections.actionCatalogFragmentToFilmDetailsFragment(
                    film
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilmsRecyclerView()
        loadFilms()
        binding.swipeRefresh?.setOnRefreshListener {
            viewModel.refreshFilms()
        }
    }

    private fun setupFilmsRecyclerView() {
        filmsAdapter = FilmsAdapter(viewModel)
        binding.catalogRv.adapter = filmsAdapter
        binding.catalogRv.addItemDecoration(FilmsCatalogItemDecorator())
        binding.catalogRv.setHasFixedSize(true)
    }

    private fun loadFilms() {
        binding.catalogRv.visibility = View.INVISIBLE
        viewModel.films.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> handleSuccessResult(result.data)
                is Result.Error -> handleErrorResult()
                is Result.Loading -> setLoadingIndicator(true)
            }
        }
    }

    private fun handleSuccessResult(films: List<Film>?) {
        setLoadingIndicator(false)
        films?.let {
            if (films.isNotEmpty()) {
                filmsAdapter?.films = films
                binding.swipeRefresh?.isRefreshing = false
                binding.catalogRv.visibility = View.VISIBLE
            }
        }
    }

    private fun handleErrorResult() {
        setLoadingIndicator(false)
        Snackbar.make(
            binding.coordinatorLayout ?: binding.root,
            requireContext().getString(R.string.load_error),
            Snackbar.LENGTH_LONG
        )
            .setAction(requireContext().getString(R.string.try_again)) {
                setLoadingIndicator(true)
                viewModel.refreshFilms()
            }
            .setActionTextColor(requireContext().getColor(R.color.white))
            .show()
    }

    private fun setLoadingIndicator(isLoading: Boolean) {
        binding.swipeRefresh?.isRefreshing = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}