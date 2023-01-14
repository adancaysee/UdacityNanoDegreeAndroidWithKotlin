package com.udacity.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.trackmysleepquality.R
import com.udacity.trackmysleepquality.database.SleepNight
import convertDurationToFormatted
import convertNumericQualityToString

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

class SleepNightAdapter : ListAdapter<SleepNight,SleepNightAdapter.ViewHolder>(SleepNightDiffCallback) {

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


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sleepLengthTexView: TextView = itemView.findViewById(R.id.sleep_length_texview)
        private val qualityStringTexView: TextView =
            itemView.findViewById(R.id.quality_string_textview)
        private val qualityImageView: ImageView = itemView.findViewById(R.id.quality_image_view)

        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            sleepLengthTexView.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            qualityStringTexView.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImageView.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sleep_night, parent, false)
                return ViewHolder(view)
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