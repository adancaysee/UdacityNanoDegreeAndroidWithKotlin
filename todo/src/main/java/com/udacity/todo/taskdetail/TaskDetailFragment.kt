package com.udacity.todo.taskdetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.udacity.todo.R
import com.udacity.todo.databinding.TaskDetailFragmentBinding

class TaskDetailFragment : Fragment(), MenuProvider {

    private lateinit var binding: TaskDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding = TaskDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.task_detail_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.title == null) {
            return false
        }
        Toast.makeText(requireContext(), menuItem.title ?: "", Toast.LENGTH_LONG).show()
        return true
    }
}