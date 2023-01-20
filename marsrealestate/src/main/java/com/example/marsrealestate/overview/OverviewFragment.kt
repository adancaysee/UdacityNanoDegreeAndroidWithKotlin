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

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}