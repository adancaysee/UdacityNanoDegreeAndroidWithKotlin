package com.udacity.gdgfinder.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.udacity.gdgfinder.R
import com.udacity.gdgfinder.databinding.FragmentAddGdgBinding

class AddGdgFragment : Fragment() {

    private val viewModel: AddGdgViewModel by lazy {
        AddGdgViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddGdgBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.showSnackbarEvent.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.application_submitted),
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.doneShowingSnackbar()

                    binding.button.text = getString(R.string.done)
                    binding.button.contentDescription = getString(R.string.submitted)
                }
            }
        }

        return binding.root
    }
}