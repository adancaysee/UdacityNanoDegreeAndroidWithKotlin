package com.udacity.gdgfinder.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.gdgfinder.databinding.FragmentGdgListBinding

class GdgListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGdgListBinding.inflate(inflater, container, false)
        return binding.root
    }
}