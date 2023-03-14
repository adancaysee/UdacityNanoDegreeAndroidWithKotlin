package com.udacity.todo.tasklist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.udacity.todo.R
import com.udacity.todo.databinding.TaskListFragmentBinding
import kotlinx.coroutines.*
import timber.log.Timber

class TaskListFragment : Fragment(), MenuProvider {

    private lateinit var binding: TaskListFragmentBinding
    private val taskListViewModel: TaskListViewModel by lazy {
        ViewModelProvider(this)[TaskListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding = TaskListFragmentBinding.inflate(inflater)
        binding.viewModel = taskListViewModel
        binding.lifecycleOwner = this


        lifecycleScope.launch {
            coroutineScope {
                launch { delay("delay1",3) }
                launch { delay("delay2",3) }
            }
            withContext(Dispatchers.IO) {
                delay("delay3",3)
                delay("delay4",3)
            }

            withContext(Dispatchers.IO) {
                coroutineScope {
                    launch { delay("delay5",3) }
                    launch { delay("delay6",3) }
                }
            }
            delay("delay7",3)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskListViewModel.addTaskEvent.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(
                    TaskListFragmentDirections.actionAddEditTask(
                        null,
                        getString(R.string.add_task)
                    )
                )
                taskListViewModel.doneAddTaskEvent()
            }
        }
    }

    suspend fun delay(text: String, delay: Long) {
        for (i in 1..delay) {
            delay(1000)
            Timber.d("$text - threadName - ${Thread.currentThread().name} -  ${1000*i}")
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.task_list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_filter -> {
            }
            R.id.menu_refresh -> {

            }
            R.id.menu_clear -> {

            }
            else -> return false
        }
        Toast.makeText(requireContext(), menuItem.title ?: "", Toast.LENGTH_LONG).show()
        return true
    }

}