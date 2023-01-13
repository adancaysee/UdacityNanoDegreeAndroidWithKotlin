package com.udacity.trackmysleepquality.sleeptracker

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.trackmysleepquality.R
import com.udacity.trackmysleepquality.database.SleepNight

/**
LayoutInflater.from(context).inflate(R.layout.item_textview,parent,false)
vs
LayoutInflater.from(context).inflate(R.layout.item_textview,parent,true)

inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
root == parent == recyclerview
if we set attachToRoot = true --> view is inflated and added automatically to the parent view

 ***
 * Recyclerview don't care about data. So you should notify if

 ***
 * Recyclerview reuse the view. So you should write set and reset logic together
 */

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.TextItemViewHolder>() {

    var sleepNights = listOf<SleepNight>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class TextItemViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    /**
    onCreateViewHolder --> Create a new view for recyclerview
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val textView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_textview, parent, false) as TextView
        return TextItemViewHolder(textView = textView)
    }

    /**
    onBindViewHolder --> Draw a view with content
     */
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = sleepNights[position]
        if (item.sleepQuality <= 1) {
            holder.textView.setTextColor(Color.RED)
        }else {
            holder.textView.setTextColor(Color.BLACK)
        }
        holder.textView.text = item.sleepQuality.toString()

    }

    override fun getItemCount(): Int = sleepNights.size


}