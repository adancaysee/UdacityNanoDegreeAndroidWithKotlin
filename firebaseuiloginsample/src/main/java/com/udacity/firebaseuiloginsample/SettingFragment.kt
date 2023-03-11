package com.udacity.firebaseuiloginsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat

class SettingFragment : PreferenceFragmentCompat() {

    private val viewModel by activityViewModels<UserViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "It's ok", Toast.LENGTH_LONG).show()
            } else {
                findNavController().navigate(SettingFragmentDirections.actionSettingDestinationToLoginDestination())
            }
        }
    }


}