package com.udacity.eggtimer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.eggtimer.databinding.FragmentEggTimerBinding


class EggTimerFragment : Fragment() {

    private val viewModel: EggTimerViewModel by lazy {
        ViewModelProvider(this)[EggTimerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEggTimerBinding.inflate(inflater, container, false)

        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}