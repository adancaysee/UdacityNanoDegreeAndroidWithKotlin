package com.udacity.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.trackmysleepquality.database.SleepNight
import com.udacity.trackmysleepquality.databinding.ItemHeaderBinding
import com.udacity.trackmysleepquality.databinding.ItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

 ***
 * binding.executePendingBindings()
 * This forces the bindings to run immediately instead of delaying them until the next frame
 */

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_SLEEP_NIGHT = 1

class SleepNightAdapter(private val sleepNightAdapterListener: SleepNightAdapterListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = if (list.isNullOrEmpty()) {
                listOf<DataItem>(DataItem.Header)
            } else {
                listOf<DataItem>(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_SLEEP_NIGHT
        }
    }

    /**
    onCreateViewHolder --> Create a new view for recyclerview
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_SLEEP_NIGHT -> SleepNightItemViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
    onBindViewHolder --> Draw a view with content
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SleepNightItemViewHolder -> {
                val item = getItem(position) as DataItem.SleepNightItem
                holder.bind(item.sleepNight, sleepNightAdapterListener)
            }
        }
    }

    class SleepNightItemViewHolder private constructor(private val binding: ItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight, sleepNightAdapterListener: SleepNightAdapterListener) {
            binding.sleepNight = item
            binding.adapterListener = sleepNightAdapterListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SleepNightItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                /**
                 * Prev
                 * val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sleep_night, parent, false)
                 * Option
                 *  val binding = DataBindingUtil.inflate<ItemSleepNightBinding>(layoutInflater,R.layout.item_sleep_night,parent,false)
                 */
                val binding = ItemSleepNightBinding.inflate(layoutInflater, parent, false)

                return SleepNightItemViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder private constructor(binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)

                return HeaderViewHolder(binding)
            }
        }

    }

    companion object SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        /**
         * Check if an item added,removed,moved
         */
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * Check if an item updated
         * Data class(SleepNight is a data class) default override equal method
         */
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

}

sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight) : DataItem() {
        override val id: Long = sleepNight.nightId
    }

    object Header : DataItem() {
        override val id: Long = Long.MIN_VALUE
    }

    abstract val id: Long
}

class SleepNightAdapterListener(val itemClickListener: (nightId: Long) -> Unit) {
    fun onClick(sleepNight: SleepNight) = itemClickListener(sleepNight.nightId)
}