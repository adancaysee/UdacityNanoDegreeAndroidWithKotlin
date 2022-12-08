package com.udacity.guesstheword.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.udacity.guesstheword.R
import com.udacity.guesstheword.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            layoutInflater,
            R.layout.fragment_title,
            container,
            false
        )

        binding.playGameButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                TitleFragmentDirections.actionTitleToGame()
            )
        )

        return binding.root
    }

}