package com.example.kinogramm.view.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.data.FavouriteFilmsDataSource
import com.example.kinogramm.data.FilmDataSource
import com.example.kinogramm.databinding.FragmentFavouriteFilmsBinding

class FavouriteFilmsFragment : Fragment() {

    companion object {
        const val NAME = "FavouriteFilmsFragment"

        fun newInstance() = FavouriteFilmsFragment()
    }

    private var _binding: FragmentFavouriteFilmsBinding? = null
    private val binding get() = _binding!!
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
    }

    private fun setUpRecyclerView() {
        binding.favouriteFilmsRv.run {
            favouriteFilmsAdapter = FavouriteFilmsAdapter()
            updateFavouriteFilmsAdapter()
            adapter = favouriteFilmsAdapter

            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            setupSwipeListener()
        }
    }

    private fun updateFavouriteFilmsAdapter() {
        favouriteFilmsAdapter.films =
            FilmDataSource.films.filter { it.id in FavouriteFilmsDataSource.likedFilmsIds }
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
                FavouriteFilmsDataSource.likedFilmsIds.remove(film.id)
                updateFavouriteFilmsAdapter()
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