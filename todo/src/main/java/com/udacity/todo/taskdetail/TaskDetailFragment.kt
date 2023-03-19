package com.udacity.todo.taskdetail

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.udacity.todo.R
import com.udacity.todo.databinding.TaskDetailFragmentBinding
import com.udacity.todo.util.EventObserver
import com.udacity.todo.util.showSnackbar

class TaskDetailFragment : Fragment(), MenuProvider {

    private lateinit var binding: TaskDetailFragmentBinding

    private val viewModel by viewModels<TaskDetailViewModel> {
        TaskDetailViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding = TaskDetailFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null) return

        val taskId = TaskDetailFragmentArgs.fromBundle(requireArguments()).taskId
        viewModel.setTaskId(taskId)

        viewModel.editTaskEvent.observe(viewLifecycleOwner, EventObserver {
            val action =
                TaskDetailFragmentDirections.actionEditTask(taskId, getString(R.string.edit_task))
            findNavController().navigate(action)
        })


        viewModel.deleteTaskEvent.observe(viewLifecycleOwner, EventObserver {
            val action = TaskDetailFragmentDirections.actionGlobalListTaskDestination()
            action.resultMessage = getString(R.string.successfully_deleted_task_message)
            findNavController().navigate(action)
        })

        viewModel.showSnackbarEvent.observe(viewLifecycleOwner, EventObserver {
            binding.root.showSnackbar(it)
        })
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.task_detail_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.title == null) {
            return false
        }
        if (menuItem.itemId == R.id.menu_delete) {
            viewModel.deleteTask()
        }
        return true
    }
}