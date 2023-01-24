package com.udacity.devbyteviewer.videos_dev_byte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.devbyteviewer.databinding.FragmentDevByteBinding

class DevByteVideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDevByteBinding.inflate(layoutInflater, container, false)

        val viewModel = ViewModelProvider(this)[DevByteViewModel::class.java]
        binding.viewModel = viewModel


        val devByteVideoAdapter =
            DevByteVideoAdapter(DevByteVideoAdapter.OnItemClickListener { video ->
                Toast.makeText(context, video.url, Toast.LENGTH_LONG).show()
            })
        binding.recyclerView.adapter = devByteVideoAdapter

        viewModel.playList.observe(viewLifecycleOwner) {
            it?.let {
                devByteVideoAdapter.submitList(it)
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}