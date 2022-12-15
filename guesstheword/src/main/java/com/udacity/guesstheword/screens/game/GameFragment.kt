package com.udacity.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.guesstheword.R
import com.udacity.guesstheword.databinding.FragmentGameBinding

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
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.gameViewModel = viewModel

        viewModel.word.observe(viewLifecycleOwner) { newWord ->
            binding.wordText.text = newWord
        }

        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        viewModel.currentTime.observe(viewLifecycleOwner) { time ->
            binding.timerText.text = DateUtils.formatElapsedTime(time)
        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        }

        return binding.root
    }

    private fun gameFinished() {
        findNavController().navigate(
            GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        )
    }

}