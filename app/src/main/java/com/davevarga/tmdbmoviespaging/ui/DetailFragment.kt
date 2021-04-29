package com.davevarga.tmdbmoviespaging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.davevarga.tmdbmoviespaging.R
import com.davevarga.tmdbmoviespaging.databinding.FragmentDetailBinding
import com.davevarga.tmdbmoviespaging.models.Movie


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val myCollectionViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            MyCollectionViewModelFactory(requireActivity().application)
        )
            .get(MyCollectionViewModel::class.java)
    }

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().setTitle("Details")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.movie = args.movieDetails

        binding.addToCollection.setOnClickListener {
            myCollectionViewModel.insert(binding.movie as Movie)
        }
        val backgroundPoster = args.movieDetails.backdropPath
        Glide.with(view)
            .load("http://image.tmdb.org/t/p/w500/" + backgroundPoster)
            .into(binding.backgroundPoster)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_up -> {
//                findNavController().popBackStack()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

}