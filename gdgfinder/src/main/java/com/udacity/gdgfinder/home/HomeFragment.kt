package com.udacity.gdgfinder.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.gdgfinder.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.navigateToSearch.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeDestinationToGdgListDestination())
                    viewModel.onNavigatedToSearch()
                }
            }

        }

        return binding.root
    }
}