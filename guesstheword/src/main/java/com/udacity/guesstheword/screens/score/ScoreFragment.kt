package com.udacity.guesstheword.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.udacity.guesstheword.R
import com.udacity.guesstheword.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentScoreBinding>(
            layoutInflater,
            R.layout.fragment_score,
            container,
            false
        )

        binding.playAgainButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                ScoreFragmentDirections.actionRestart()
            )
        )

        return binding.root
    }

}