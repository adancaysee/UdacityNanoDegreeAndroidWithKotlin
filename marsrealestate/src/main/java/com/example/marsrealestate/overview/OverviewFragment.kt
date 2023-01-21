package com.example.marsrealestate.overview

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marsrealestate.R
import com.example.marsrealestate.databinding.FragmentOverviewBinding
import com.example.marsrealestate.network.MarsApiFilter

class OverviewFragment : Fragment(), MenuProvider {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val binding = FragmentOverviewBinding.inflate(inflater, container, false)

        overviewViewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        binding.overviewViewModel = overviewViewModel
        binding.lifecycleOwner = this

        val adapter = PhotoGridAdapter(PhotoGridAdapter.OnItemClickListener { marsProperty ->
            overviewViewModel.displayPropertyDetails(marsProperty)
        })
        binding.photosGrid.adapter = adapter

        overviewViewModel.navigateToDetailEvent.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewDestinationToDetailDestination(
                        it
                    )
                )
                overviewViewModel.doneNavigating()
            }
        }
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        overviewViewModel.updateFilter(
            when (menuItem.itemId) {
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }
}