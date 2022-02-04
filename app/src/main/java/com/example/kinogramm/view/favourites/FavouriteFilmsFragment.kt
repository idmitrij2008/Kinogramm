package com.example.kinogramm.view.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.databinding.FragmentFavouriteFilmsBinding

@ExperimentalPagingApi
class FavouriteFilmsFragment : Fragment() {
    private var _binding: FragmentFavouriteFilmsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavouriteFilmsViewModel
    private lateinit var favouriteFilmsAdapter: FavouriteFilmsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            FavouriteFilmsViewModelFactory(requireActivity().application)
        ).get(
            FavouriteFilmsViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.favouriteFilms.observe(viewLifecycleOwner) {
            favouriteFilmsAdapter.films = it
        }
    }

    private fun setUpRecyclerView() {
        binding.favouriteFilmsRv.run {
            favouriteFilmsAdapter = FavouriteFilmsAdapter()
            adapter = favouriteFilmsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        setupSwipeListener()
    }

    private fun setupSwipeListener() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val film = favouriteFilmsAdapter.films[viewHolder.bindingAdapterPosition]
                viewModel.removeFromFavourites(film)
                Toast.makeText(
                    requireContext(),
                    "Film ${film.title} deleted from favourites.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.favouriteFilmsRv)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}