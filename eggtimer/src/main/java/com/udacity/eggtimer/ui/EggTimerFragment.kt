package com.udacity.eggtimer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.udacity.eggtimer.R
import com.udacity.eggtimer.databinding.FragmentEggTimerBinding

private const val TOPIC = "breakfast"
class EggTimerFragment : Fragment() {

    private val viewModel: EggTimerViewModel by lazy {
        ViewModelProvider(this)[EggTimerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEggTimerBinding.inflate(inflater, container, false)

        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        subscribeTopic()

        return binding.root
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC).addOnCompleteListener {task->
            val message = if (task.isSuccessful) {
                getString(R.string.message_subscribed)
            }else{
                getString(R.string.message_subscribe_failed)
            }
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }
    }
}