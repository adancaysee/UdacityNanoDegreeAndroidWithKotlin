package com.udacity.trackmysleepquality.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.trackmysleepquality.R
import com.udacity.trackmysleepquality.database.SleepDatabase
import com.udacity.trackmysleepquality.databinding.FragmentSleepTrackerBinding

class SleepTrackerFragment : Fragment() {

    private lateinit var binding: FragmentSleepTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val database = SleepDatabase.getInstance(application)
        val sleepDao = database.sleepDao

        val viewModelFactory = SleepTrackerViewModelFactory(sleepDao, application)

        val sleepTrackerViewModel =
            ViewModelProvider(this, viewModelFactory)[SleepTrackerViewModel::class.java]
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        binding.lifecycleOwner = this

        val adapter = SleepNightAdapter()
        binding.sleepNightList.adapter = adapter

        sleepTrackerViewModel.nights.observe(viewLifecycleOwner) {
            /**
             * LiveData observers are sometimes passed null
             */
            it?.let {
                adapter.sleepNights = it
            }
        }

        sleepTrackerViewModel.navigateToSleepQualityEvent.observe(viewLifecycleOwner) { night ->
            night?.let {
                findNavController().navigate(
                    SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
                        night.nightId
                    )
                )
                sleepTrackerViewModel.doneNavigating()
            }

        }

        sleepTrackerViewModel.showSnackbarEvent.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_LONG
                ).show()
                sleepTrackerViewModel.doneShowingSnackbar()
            }
        }

        return binding.root
    }

}