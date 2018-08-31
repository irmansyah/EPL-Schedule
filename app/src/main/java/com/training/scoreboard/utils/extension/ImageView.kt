package com.training.scoreboard.utils.extension

import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by irmansyah on 25/03/18.
 */
internal fun ImageView.loadImage(url: String) {
    if (!url.isEmpty()) { Glide.with(this.context).load(url).asBitmap().centerCrop().into(this) }
}

internal fun ImageView.loadImage(bitmap: Bitmap) {
    Glide.with(this.context).load(bitmap).asBitmap().centerCrop().into(this)
}