package com.udacity.devbyteviewer.videos_dev_byte

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.udacity.devbyteviewer.R


@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val uri = it.toUri().buildUpon().scheme("https").build()
        imageView.load(uri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}