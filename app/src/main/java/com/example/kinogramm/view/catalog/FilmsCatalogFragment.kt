package com.example.kinogramm.view.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.databinding.FragmentFilmsCatalogBinding
import com.example.kinogramm.view.main.FilmsCatalogItemDecorator
import kotlinx.coroutines.launch

private val TAG = "FilmsCatalogFragment"

@ExperimentalPagingApi
class FilmsCatalogFragment : Fragment() {
    private var _binding: FragmentFilmsCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FilmsCatalogViewModel
    private var filmsAdapter: FilmsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            FilmCatalogViewModelFactory(requireActivity().application)
        ).get(
            FilmsCatalogViewModel::class.java
        )

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
//        binding.swipeRefresh?.setOnRefreshListener {
//            viewModel.refreshFilms()
//        }

        viewModel.films.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                filmsAdapter?.submitData(pagingData)
            }
        }
    }

    private fun setupFilmsRecyclerView() {
        filmsAdapter = FilmsAdapter(viewModel)
        binding.catalogRv.adapter = filmsAdapter
        binding.catalogRv.addItemDecoration(FilmsCatalogItemDecorator())
        binding.catalogRv.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}