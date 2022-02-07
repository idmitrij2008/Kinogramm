package com.example.kinogramm.view.details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.R
import com.example.kinogramm.databinding.FragmentFilmDetailsBinding
import com.example.kinogramm.util.hideKeyBoard

@ExperimentalPagingApi
class FilmDetailsFragment : Fragment() {
    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<FilmDetailsFragmentArgs>()
    private lateinit var viewModel: FilmDetailsViewModel
    private val likeButtonObserver: Observer<Boolean> by lazy {
        Observer { isLiked ->
            val newColor = if (isLiked) {
                R.color.red_heart
            } else R.color.gray_icon_alpha_50

            binding.likeButton?.imageTintList =
                ColorStateList.valueOf(requireContext().getColor(newColor))

            binding.likeFab?.imageTintList =
                ColorStateList.valueOf(requireContext().getColor(newColor))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            FilmDetailsViewModelFactory(requireActivity().application, args.film.id)
        ).get(
            FilmDetailsViewModel::class.java
        )
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.mainAppbar?.updateLayoutParams {
            height = 2 * view.resources.displayMetrics.heightPixels / 4
        }
        viewModel.isLiked.observe(viewLifecycleOwner, likeButtonObserver)
        setCommentEditText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCommentEditText() {
        binding.userComment?.setOnEditorActionListener { commentEditText, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
                commentEditText.text.toString().let { comment ->
                    if (comment.isBlank()) {
                        viewModel.removeComment()
                    } else {
                        viewModel.updateComment(comment)
                    }
                }
                commentEditText.hideKeyBoard()
                true
            } else false
        }
    }
}