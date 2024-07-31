package com.example.book.utils.helper

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}
