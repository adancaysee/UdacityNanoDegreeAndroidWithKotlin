package com.udacity.todo.tasklist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.udacity.todo.R
import com.udacity.todo.databinding.FragmentListTaskBinding

class ListTaskFragment : Fragment(), MenuProvider {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val binding = FragmentListTaskBinding.inflate(inflater)
        return binding.root
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