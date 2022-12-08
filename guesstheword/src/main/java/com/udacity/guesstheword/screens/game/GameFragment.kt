package com.udacity.guesstheword.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.guesstheword.R
import com.udacity.guesstheword.databinding.FragmentGameBinding
import timber.log.Timber


/**
 * UIController only displays and gets user/OS events
 * ViewModels hold data for UI
 * ViewModel make decision and process data
 * ViewModel never references fragments,activities or views
 */

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentGameBinding>(
            layoutInflater,
            R.layout.fragment_game,
            container,
            false
        )

        Timber.i("Called ViewModelProvider()")
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            updateWordText()
            updateScoreText()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            updateWordText()
            updateScoreText()
        }

        updateWordText()
        updateScoreText()

        return binding.root
    }

    private fun gameFinished() {
        findNavController().navigate(GameFragmentDirections.actionGameToScore(viewModel.score))
    }

    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.toString()
    }
}