package com.example.marsrealestate.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marsrealestate.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val overviewViewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        binding.overviewViewModel = overviewViewModel
        binding.lifecycleOwner = this

        val adapter = PhotoGridAdapter(PhotoGridAdapter.OnItemClickListener { marsProperty ->
            overviewViewModel.displayPropertyDetails(marsProperty)
        })
        binding.photosGrid.adapter = adapter

        overviewViewModel.navigateToDetailEvent.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(OverviewFragmentDirections.actionOverviewDestinationToDetailDestination(it))
                overviewViewModel.doneNavigating()
            }


        }

        return binding.root
    }
}