package com.udacity.trackmysleepquality.sleepquality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.udacity.trackmysleepquality.R
import com.udacity.trackmysleepquality.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {

    private lateinit var binding: FragmentSleepQualityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)
        return binding.root
    }


}