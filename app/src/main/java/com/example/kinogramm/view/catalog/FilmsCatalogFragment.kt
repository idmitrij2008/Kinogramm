package com.example.kinogramm.view.catalog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.kinogramm.R
import com.example.kinogramm.databinding.FragmentFilmsCatalogBinding
import com.example.kinogramm.view.KinogrammApp
import com.example.kinogramm.view.MainViewModel
import com.example.kinogramm.view.ViewModelFactory
import com.example.kinogramm.view.main.FilmsCatalogItemDecorator
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private val TAG = "FilmsCatalogFragment"

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class FilmsCatalogFragment : Fragment() {
    private var _binding: FragmentFilmsCatalogBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private var filmsAdapter: FilmsAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FilmsCatalogViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FilmsCatalogViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as KinogrammApp).component
            .activityComponentFactory()
            .create()
            .filmCatalogComponentFactory()
            .create()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            mainViewModel.lastClickedFilmId = film.remoteId
            findNavController().navigate(
                FilmsCatalogFragmentDirections.actionCatalogFragmentToFilmDetailsFragment(
                    film.remoteId
                )
            )
        }
    }

    private fun observeNewFilmsData() {
        viewModel.films
            .subscribeOn(Schedulers.io())
            .subscribe { pagingData ->
                filmsAdapter?.submitData(lifecycle, pagingData)
            }
    }

    private fun observeLoadState() {
        lifecycleScope.launchWhenStarted {
            filmsAdapter?.loadStateFlow?.collectLatest { loadStates ->
                binding.swipeRefresh?.isRefreshing = loadStates.refresh is LoadState.Loading
                if (loadStates.refresh is LoadState.Error) {
                    Log.d(TAG, "LoadState.Error")
                    handleErrorResult()
                }
            }
        }
    }

    private fun setupFilmsRecyclerView() {
        filmsAdapter = FilmsAdapter(viewModel, mainViewModel.lastClickedFilmId)
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