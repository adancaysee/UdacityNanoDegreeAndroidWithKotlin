package com.udacity.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.trackmysleepquality.database.SleepNight
import com.udacity.trackmysleepquality.databinding.ItemSleepNightBinding

/**
LayoutInflater.from(context).inflate(R.layout.item_textview,parent,false)
vs
LayoutInflater.from(context).inflate(R.layout.item_textview,parent,true)

inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
root == parent == recyclerview
if we set attachToRoot = true --> view is inflated and added automatically to the parent view

 ***
 * notifyDataSetChanged() --> Recyclerview don't care about data. So you should notify it

 ***
 * Set/Reset
 * Recyclerview reuse the view. So you should write set and reset logic together

 ***
 * Separate concerns
 * The adapter is responsible for adapting our data to RecyclerView API
 * The viewHolder is responsible for everything related to manage views
 */

class SleepNightAdapter :
    ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback) {

    /**
    onCreateViewHolder --> Create a new view for recyclerview
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
    onBindViewHolder --> Draw a view with content
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(private val binding: ItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight) {
            binding.sleepNight = item
            /**
             * This forces the bindings to run immediately instead of delaying them until the next frame
             */
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                /**
                 * Prev
                 * val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sleep_night, parent, false)
                 * Option
                 *  val binding = DataBindingUtil.inflate<ItemSleepNightBinding>(layoutInflater,R.layout.item_sleep_night,parent,false)
                 */
                val binding = ItemSleepNightBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    object SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        /**
         * Check if an item added,removed,moved
         */
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        /**
         * Check if an item updated
         * Data class(SleepNight is a data class) default override equal method
         */
        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }

    }

}