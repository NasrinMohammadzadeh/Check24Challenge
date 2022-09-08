package com.example.check24challenge.system

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.check24challenge.di.GlideApp

fun ImageView.loadImageWithGlideMediumRadius(url: String?) {
    GlideApp.with(this)
        .load(url)
        .apply(AppGlideExtensions.mediumRadius(context))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun TextView.setDate(date : Long?){
    date?.let {
        text = DateAndTime.convertLongToStringTimeStampUTC(it)
    }
}