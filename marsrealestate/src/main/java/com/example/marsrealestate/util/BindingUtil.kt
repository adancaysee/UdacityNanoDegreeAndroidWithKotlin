package com.example.marsrealestate.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marsrealestate.R

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