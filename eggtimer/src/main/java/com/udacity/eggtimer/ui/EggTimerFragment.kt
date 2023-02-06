package com.udacity.eggtimer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.eggtimer.databinding.FragmentEggTimerBinding

class EggTimerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEggTimerBinding.inflate(inflater,container,false)
        return binding.root
    }
}