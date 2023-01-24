package com.udacity.devbyteviewer.videos_dev_byte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.udacity.devbyteviewer.databinding.FragmentDevByteBinding

class DevByteVideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDevByteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}