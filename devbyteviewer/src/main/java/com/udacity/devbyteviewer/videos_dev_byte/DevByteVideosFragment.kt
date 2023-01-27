package com.udacity.devbyteviewer.videos_dev_byte

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.devbyteviewer.databinding.FragmentDevByteBinding
import com.udacity.devbyteviewer.domain.DevByteVideo

class DevByteVideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDevByteBinding.inflate(layoutInflater, container, false)


        val viewModel =
            ViewModelProvider(this, DevByteViewModel.Factory)[DevByteViewModel::class.java]
        binding.viewModel = viewModel

        val devByteVideoAdapter =
            DevByteVideoAdapter(DevByteVideoAdapter.OnItemClickListener { video ->
                openVideo(video)
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

    private fun openVideo(video: DevByteVideo) {
        val packageManager = context?.packageManager ?: return
        var intent = Intent(Intent.ACTION_VIEW, video.launchUri)
        if (intent.resolveActivity(packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
        }
        startActivity(intent)
    }

    private val DevByteVideo.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }

}

