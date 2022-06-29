package com.test.movieapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.test.movieapp.R

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, urlString: String?) {
    urlString?.let {
        Glide.with(imageView)
            .load(urlString)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(imageView)
    }
}