package com.example.kinogramm.view.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.databinding.FragmentFavouriteFilmsBinding

class FavouriteFilmsFragment : Fragment() {
    private var _binding: FragmentFavouriteFilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavouriteFilmsViewModel by viewModels()
    private lateinit var favouriteFilmsAdapter: FavouriteFilmsAdapter

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
                val film = favouriteFilmsAdapter.films[viewHolder.adapterPosition]
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