package com.udacity.motionlayout

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.udacity.motionlayout.databinding.FragmentAnimationListBinding


data class Step(
    val number: String,
    val name: String,
    val caption: String,
    val destinationId: Int = 0,
    val highlight: Boolean = false
)

private val data = listOf(
    Step(
        "Step 1",
        "Animations with Motion Layout",
        "Learn how to build a basic animation with Motion Layout. This will crash until you complete the step in the codelab.",
        R.id.action_step1
    ),
    Step(
        "Step 2",
        "Animating based on drag events",
        "Learn how to control animations with drag events. This will not display any animation until you complete the step in the codelab.",
        R.id.action_step2
    ),
    Step(
        "Step 3",
        "Modifying a path",
        "Learn how to use KeyFrames to modify a path between start and end.",
        R.id.action_step3
    ),
    Step(
        "Step 4",
        "Building complex paths",
        "Learn how to use KeyFrames to build complex paths through multiple KeyFrames.",
        R.id.step4_destination
    ),
    Step(
        "Step 5",
        "Changing attributes with motion",
        "Learn how to resize and rotate views during animations.",
    ),
    Step(
        "Step 6",
        "Changing custom attributes",
        "Learn how to change custom attributes during motion.",
    ),
    Step(
        "Step 7",
        "OnSwipe with complex paths",
        "Learn how to control motion through complex paths with OnSwipe.",
    ),
    Step(
        "Completed: Steps 2-7",
        "Steps 2-7 completed",
        "All changes in steps 2-7 applied",
        highlight = true
    ),
    Step(
        "Step 8",
        "Running motion with code",
        "Learn how to use MotionLayout to build complex collapsing toolbar animations.",
    ),
    Step(
        "Completed: Step 8 ",
        "Implements running motion with code",
        "Changes applied from step 8",
        highlight = true
    )
)

class AnimationListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAnimationListBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = MainAdapter(data)

        return binding.root
    }
}

class MainAdapter(private val data: List<Step>) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MainViewHolder(view as CardView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

}

class MainViewHolder(private val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
    private val header: TextView = cardView.findViewById(R.id.header)
    private val description: TextView = cardView.findViewById(R.id.description)
    private val caption: TextView = cardView.findViewById(R.id.caption)

    fun bind(step: Step) {
        header.text = step.number
        description.text = step.name
        caption.text = step.caption
        val context = cardView.context
        cardView.setOnClickListener {
            cardView.findNavController().navigate(step.destinationId)
        }
        val color = if (step.highlight) {
            ContextCompat.getColor(context, R.color.orange_200)
        } else {
            MaterialColors.getColor(
                context,
                com.google.android.material.R.attr.colorOnSurface,
                Color.BLACK
            )
        }
        header.setTextColor(color)
        description.setTextColor(color)
    }

}
