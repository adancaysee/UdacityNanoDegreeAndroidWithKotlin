package com.example.marsrealestate.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.databinding.FragmentOverviewBinding
import com.example.marsrealestate.databinding.GridViewItemBinding

class OverviewFragment : Fragment() {

    private lateinit var binding: GridViewItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = GridViewItemBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        //binding.overviewViewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.response.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_LONG).show()
            binding.marsProperty = it[3]
        }

        return binding.root
    }
}