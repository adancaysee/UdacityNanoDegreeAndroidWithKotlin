package com.udacity.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.udacity.motionlayout.databinding.FragmentStep8Binding

/**
 * We did CollapsingToolbar effect with MotionLayout
 */


class Step8Fragment : Fragment() {

    private lateinit var binding: FragmentStep8Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar!!.hide()
        binding = FragmentStep8Binding.inflate(inflater, container, false)

        coordinateMotion()

        return binding.root
    }

    private fun coordinateMotion() {
        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / binding.appbarLayout.totalScrollRange.toFloat()
            binding.motionLayout.progress = seekPosition
        }
        binding.appbarLayout.addOnOffsetChangedListener(listener)
    }
}