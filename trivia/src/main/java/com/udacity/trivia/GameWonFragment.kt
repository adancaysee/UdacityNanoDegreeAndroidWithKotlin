package com.udacity.trivia

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.udacity.trivia.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment(), MenuProvider {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //add menu
        val host: MenuHost = requireActivity()
        host.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        val binding = DataBindingUtil.inflate<FragmentGameWonBinding>(
            inflater,
            R.layout.fragment_game_won,
            container,
            false
        )
        binding.nextMatchButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                GameWonFragmentDirections.actionGameWonFragmentToGameFragment()
            )
        )
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.winner_menu, menu)
        if (getFluentShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.share).isVisible = false
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.share -> {
                shareSuccess()
                true
            }
            else -> false
        }
    }

    private fun shareSuccess() {
        startActivity(getFluentShareIntent())
    }


    @Suppress("unused")
    private fun getShareIntent(): Intent {
        val arguments = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_success_text, arguments.numCorrect, arguments.numQuestions)
            )
        return shareIntent

    }

    private fun getFluentShareIntent(): Intent {
        val arguments = GameWonFragmentArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder(requireActivity()).setType("text/plain")
            .setText(
                getString(R.string.share_success_text, arguments.numCorrect, arguments.numQuestions)
            )
            .intent


    }


}