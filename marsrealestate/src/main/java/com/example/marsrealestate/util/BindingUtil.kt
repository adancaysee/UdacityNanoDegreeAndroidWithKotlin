package com.example.marsrealestate.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marsrealestate.R
import com.example.marsrealestate.network.MarsProperty
import com.example.marsrealestate.overview.PhotoGridAdapter


@BindingAdapter("listData")
fun bindListData(recyclerView: RecyclerView,list: List<MarsProperty>?) {
    list?.let {
        val adapter = recyclerView.adapter as PhotoGridAdapter
        adapter.submitList(list)
    }

}

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val uri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }

}