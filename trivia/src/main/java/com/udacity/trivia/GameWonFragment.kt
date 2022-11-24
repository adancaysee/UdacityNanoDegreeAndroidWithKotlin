package com.udacity.trivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.trivia.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentGameWonBinding>(
            inflater,
            R.layout.fragment_game_won,
            container,
            false
        )
        binding.nextMatchButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_gameWonFragment_to_gameFragment))
        return binding.root
    }
}