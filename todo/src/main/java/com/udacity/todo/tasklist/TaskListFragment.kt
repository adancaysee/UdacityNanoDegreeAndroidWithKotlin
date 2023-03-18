package com.udacity.todo.tasklist

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.todo.R
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.databinding.TaskListFragmentBinding
import com.udacity.todo.util.EventObserver
import com.udacity.todo.util.showSnackbar

/**
 * Note: We use coordinator layout. Because we want to show snackbar
 */
class TaskListFragment : Fragment(), MenuProvider {

    private lateinit var binding: TaskListFragmentBinding

    private val taskListViewModel by viewModels<TaskListViewModel> {
        TaskListViewModel.Factory
    }

    private val tasksAdapter = TasksAdapter(object : TasksAdapter.OnClickListener {
        override fun changeTaskActivateStatus(task: Task, isCompleted: Boolean) {
            taskListViewModel.changeTaskActivateStatus(task, isCompleted)
        }

        override fun openTask(taskId: String) {
            taskListViewModel.navigateToTaskDetail("")
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding = TaskListFragmentBinding.inflate(inflater)
        binding.viewModel = taskListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.tasksRecyclerView.adapter = tasksAdapter

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.isRefreshing = taskListViewModel.dataLoading.value ?: false

        taskListViewModel.tasks.observe(viewLifecycleOwner) {
            it?.let {
                tasksAdapter.submitList(it)
            }
        }

        taskListViewModel.newTaskEvent.observe(viewLifecycleOwner) {
            val action =
                TaskListFragmentDirections.actionAddEditTask(null, getString(R.string.add_task))
            findNavController().navigate(action)
        }

        taskListViewModel.openTaskEvent.observe(viewLifecycleOwner, EventObserver {
            val action = TaskListFragmentDirections.actionOpenTaskDetail()
            findNavController().navigate(action)
        })

        taskListViewModel.snackbarTextEvent.observe(viewLifecycleOwner) {
            binding.root.showSnackbar(it.peekContent(),Snackbar.LENGTH_LONG)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.task_list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_filter -> {
                showFilteringPopupMenu(requireActivity().findViewById(R.id.menu_filter))
            }
            R.id.menu_refresh -> {
                taskListViewModel.refreshTasks()
            }
            R.id.menu_clear -> {
                taskListViewModel.clearCompletedTasks()
            }
            else -> return false
        }
        return true
    }

    private fun showFilteringPopupMenu(view: View) {
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_tasks_menu, menu)

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.all -> taskListViewModel.filterTasks(TasksFilterType.ALL_TASKS)
                    R.id.active -> taskListViewModel.filterTasks(TasksFilterType.ACTIVE_TASKS)
                    R.id.completed -> taskListViewModel.filterTasks(TasksFilterType.COMPLETED_TASKS)
                }
                true
            }
            show()
        }
    }
}