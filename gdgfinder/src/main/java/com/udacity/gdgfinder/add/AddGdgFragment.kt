package com.udacity.gdgfinder.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.gdgfinder.databinding.FragmentAddGdgBinding

class AddGdgFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddGdgBinding.inflate(inflater, container, false)
        return binding.root
    }
}