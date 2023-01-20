package com.example.marsrealestate.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        binding.overviewViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = PhotoGridAdapter(PhotoGridAdapter.OnItemClickListener { marsProperty ->
            Toast.makeText(context, marsProperty.imgSrcUrl, Toast.LENGTH_LONG).show()
        })
        binding.photosGrid.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}