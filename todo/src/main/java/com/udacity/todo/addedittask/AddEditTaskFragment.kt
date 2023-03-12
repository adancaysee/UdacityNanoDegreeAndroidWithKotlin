package com.udacity.todo.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.todo.databinding.AddEditTaskFragmentBinding

class AddEditTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddEditTaskFragmentBinding.inflate(inflater)
        return binding.root
    }
}