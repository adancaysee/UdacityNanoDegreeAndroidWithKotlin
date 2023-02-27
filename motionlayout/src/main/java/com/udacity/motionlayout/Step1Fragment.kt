package com.udacity.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.motionlayout.databinding.FragmentStep1Binding

class Step1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStep1Binding.inflate(inflater, container, false)

        binding.motionLayout.progress = 1f

        return binding.root
    }
}