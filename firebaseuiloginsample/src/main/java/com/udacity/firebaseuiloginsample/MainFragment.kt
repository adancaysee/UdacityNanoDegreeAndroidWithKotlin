package com.udacity.firebaseuiloginsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.firebaseuiloginsample.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(inflater)

        binding.authButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainDestinationToLoginDestination())
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainDestinationToSettingDestination())
        }

        return binding.root
    }
}