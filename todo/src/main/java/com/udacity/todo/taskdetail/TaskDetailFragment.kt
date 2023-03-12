package com.udacity.todo.taskdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.todo.databinding.TaskDetailFragmentBinding

class TaskDetailFragment : Fragment() {

    private lateinit var binding: TaskDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TaskDetailFragmentBinding.inflate(inflater)
        return binding.root
    }
}