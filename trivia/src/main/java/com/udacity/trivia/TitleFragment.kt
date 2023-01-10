package com.udacity.trivia

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.trivia.databinding.FragmentTitleBinding


class TitleFragment : Fragment(), MenuProvider {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

        /*
        binding.playButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }
         */
        binding.playButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        )

        return binding.root
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.overflow_menu, menu)
    }

    /**
     * We don't create an actions for menu items. Because menus are used to navigate more than one destination
     */
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            menuItem, requireView().findNavController()
        )
    }

}