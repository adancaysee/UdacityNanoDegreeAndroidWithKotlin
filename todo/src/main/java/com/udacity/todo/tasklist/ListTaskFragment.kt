package com.udacity.todo.tasklist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.udacity.todo.R
import com.udacity.todo.databinding.FragmentListTaskBinding
import com.udacity.todo.util.MenuProviderViewModel

class ListTaskFragment : Fragment(), MenuProvider {

    private val menuProviderViewModel by activityViewModels<MenuProviderViewModel>()

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
        if(menuItem.title == null) {
            menuProviderViewModel.selectDrawerMenu.value = true
            return false
        }
        Toast.makeText(requireContext(), menuItem.title ?: "drawer menu", Toast.LENGTH_LONG).show()
        return true
    }

}