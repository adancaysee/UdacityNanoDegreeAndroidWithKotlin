package com.udacity.trackmysleepquality.sleepquality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.trackmysleepquality.R
import com.udacity.trackmysleepquality.database.SleepDatabase
import com.udacity.trackmysleepquality.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {

    private lateinit var binding: FragmentSleepQualityBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)


        val application = requireNotNull(activity).application


        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())

        val database = SleepDatabase.getInstance(application)
        val sleepDao = database.sleepDao

        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey,sleepDao)

        val sleepQualityViewModel =
            ViewModelProvider(this, viewModelFactory)[SleepQualityViewModel::class.java]

        binding.sleepQualityViewModel = sleepQualityViewModel

        sleepQualityViewModel.navigateToSleepTrackerEvent.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
                //findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                sleepQualityViewModel.doneNavigating()

            }

        }

        return binding.root
    }


}