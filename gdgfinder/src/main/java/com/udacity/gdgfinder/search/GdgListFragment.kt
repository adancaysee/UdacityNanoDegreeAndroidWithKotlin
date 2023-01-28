package com.udacity.gdgfinder.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.gdgfinder.databinding.FragmentGdgListBinding

class GdgListFragment : Fragment() {

    private val viewModel: GdgListViewModel by lazy {
        ViewModelProvider(this, GdgListViewModel.Factory)[GdgListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGdgListBinding.inflate(inflater, container, false)

        val adapter = GdgListAdapter(GdgListAdapter.ItemClickListener {
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        })
        binding.gdgChapterList.adapter = adapter

        viewModel.chapter.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        binding.gdgListViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}