package com.udacity.todo.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.udacity.todo.databinding.AddEditTaskFragmentBinding
import com.udacity.todo.util.EventObserver
import com.udacity.todo.util.showSnackbar

class AddEditTaskFragment : Fragment() {

    private val viewModel by viewModels<AddEditTaskViewModel> {
        AddEditTaskViewModel.Factory
    }

    private lateinit var binding: AddEditTaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEditTaskFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val taskId = AddEditTaskFragmentArgs.fromBundle(it).taskId
            viewModel.setTaskId(taskId)
        }

        viewModel.showSnackbarEvent.observe(viewLifecycleOwner,EventObserver {
            binding.root.showSnackbar(it)
        })

        viewModel.resultMessageEvent.observe(viewLifecycleOwner,EventObserver {
            val action = AddEditTaskFragmentDirections.actionGlobalListTaskDestination()
            action.resultMessage = it
            findNavController().navigate(action)
        })
    }
}