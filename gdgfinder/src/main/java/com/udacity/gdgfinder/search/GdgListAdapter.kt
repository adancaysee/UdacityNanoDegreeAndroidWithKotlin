package com.udacity.gdgfinder.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.gdgfinder.databinding.ItemGdgBinding
import com.udacity.gdgfinder.domain.GdgChapter

class GdgListAdapter(private val itemClickListener: ItemClickListener) :
    ListAdapter<GdgChapter, GdgListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder private constructor(val binding: ItemGdgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GdgChapter, itemClickListener: ItemClickListener) {
            binding.chapter = item
            binding.itemClickListener = itemClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGdgBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<GdgChapter>() {
        override fun areItemsTheSame(oldItem: GdgChapter, newItem: GdgChapter): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GdgChapter, newItem: GdgChapter): Boolean {
            return oldItem == newItem
        }

    }

    class ItemClickListener(private val onItemClick: (GdgChapter) -> Unit) {
        fun onClick(item: GdgChapter) = onItemClick(item)
    }


}