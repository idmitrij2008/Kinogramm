package com.example.kinogramm.view.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.kinogramm.R
import com.example.kinogramm.databinding.FragmentFilmsCatalogBinding
import com.example.kinogramm.view.main.FilmsCatalogItemDecorator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
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

        observeShouldShowDetails()
        observeLoadState()
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
        binding.swipeRefresh?.setOnRefreshListener {
            filmsAdapter?.refresh()
        }

        observeNewFilmsData()
    }

    private fun observeShouldShowDetails() {
        viewModel.showDetailsForFilm.observe(this) { film ->
            findNavController().navigate(
                FilmsCatalogFragmentDirections.actionCatalogFragmentToFilmDetailsFragment(
                    film
                )
            )
        }
    }

    private fun observeNewFilmsData() {
        viewModel.films.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                filmsAdapter?.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState() {
        lifecycleScope.launchWhenStarted {
            filmsAdapter?.loadStateFlow?.collectLatest { loadStates ->
                binding.swipeRefresh?.isRefreshing = loadStates.refresh is LoadState.Loading
                if (loadStates.refresh is LoadState.Error) {
                    Log.d(TAG, "Error")
                    handleErrorResult()
                }
            }
        }
    }

    private fun setupFilmsRecyclerView() {
        filmsAdapter = FilmsAdapter(viewModel)
        binding.catalogRv.adapter = filmsAdapter
        binding.catalogRv.addItemDecoration(FilmsCatalogItemDecorator())
        binding.catalogRv.setHasFixedSize(true)
    }

    private fun handleErrorResult() {
        Snackbar.make(
            binding.coordinatorLayout ?: binding.root,
            requireContext().getString(R.string.load_error),
            Snackbar.LENGTH_LONG
        )
            .setAction(requireContext().getString(R.string.try_again)) {
                filmsAdapter?.refresh()
            }
            .setActionTextColor(requireContext().getColor(R.color.white))
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}