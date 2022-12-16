package com.udacity.guesstheword.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.guesstheword.R
import com.udacity.guesstheword.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {

    private lateinit var scoreViewModel: ScoreViewModel
    private lateinit var scoreViewModelFactory: ScoreViewModelFactory

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

        val args:ScoreFragmentArgs by navArgs()
        scoreViewModelFactory = ScoreViewModelFactory(args.score)
        scoreViewModel = ViewModelProvider(this,scoreViewModelFactory)[ScoreViewModel::class.java]

        binding.scoreViewModel = scoreViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        scoreViewModel.eventPlayAgain.observe(viewLifecycleOwner) { isPlayAgain->
            if (isPlayAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                scoreViewModel.onPlayAgainComplete()
            }
        }

        return binding.root
    }

}