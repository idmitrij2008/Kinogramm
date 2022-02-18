package com.example.kinogramm.view.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinogramm.databinding.FragmentFavouriteFilmsBinding
import com.example.kinogramm.util.showShortToast
import com.example.kinogramm.view.KinogrammApp
import com.example.kinogramm.view.ViewModelFactory
import javax.inject.Inject

@ExperimentalPagingApi
class FavouriteFilmsFragment : Fragment() {
    private var _binding: FragmentFavouriteFilmsBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteFilmsAdapter: FavouriteFilmsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FavouriteFilmsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouriteFilmsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as KinogrammApp).component
            .activityComponentFactory()
            .create(0)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                requireContext().showShortToast("Film ${film.title} deleted from favourites.")
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.favouriteFilmsRv)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}