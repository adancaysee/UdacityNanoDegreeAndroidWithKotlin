package com.example.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val marsProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val application = requireActivity().application
        val detailViewModelFactory = DetailViewModelFactory(marsProperty, application)
        val detailViewModel =
            ViewModelProvider(this, detailViewModelFactory)[DetailViewModel::class.java]
        binding.detailViewModel = detailViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}