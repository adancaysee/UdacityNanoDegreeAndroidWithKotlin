package com.udacity.devbyteviewer.videos_dev_byte

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.devbyteviewer.databinding.DevbyteItemBinding
import com.udacity.devbyteviewer.domain.DevByteVideo

class DevByteVideoAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<DevByteVideo, DevByteVideoAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class ViewHolder private constructor(private val binding: DevbyteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: DevByteVideo, itemClickListener: OnItemClickListener) {
            binding.video = video
            binding.onItemClickListener = itemClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    DevbyteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<DevByteVideo>() {
        override fun areItemsTheSame(oldItem: DevByteVideo, newItem: DevByteVideo): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: DevByteVideo, newItem: DevByteVideo): Boolean {
            return oldItem == newItem
        }

    }

    class OnItemClickListener(val onItemClick: (DevByteVideo) -> Unit) {
        fun onClick(item: DevByteVideo) = onItemClick(item)
    }


}