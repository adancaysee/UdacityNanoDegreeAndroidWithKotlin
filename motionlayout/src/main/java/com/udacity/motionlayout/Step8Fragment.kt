package com.udacity.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.motionlayout.databinding.FragmentStep7Binding

class Step8Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStep7Binding.inflate(inflater, container, false)


        return binding.root
    }
}