package com.kryzl.meetthedevs.image

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView

interface RemoteImageHandler {

    fun setImageFromUrl(imageView: ImageView, url: String, signature: String)

    fun getBitmapFromUrl(context: Context, url: String, signature: String): Bitmap?
}
