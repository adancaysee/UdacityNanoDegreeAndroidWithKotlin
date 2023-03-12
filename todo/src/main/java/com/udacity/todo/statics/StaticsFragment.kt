package com.udacity.todo.statics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.todo.databinding.StaticsFragmentBinding

class StaticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = StaticsFragmentBinding.inflate(inflater)
        return binding.root
    }
}