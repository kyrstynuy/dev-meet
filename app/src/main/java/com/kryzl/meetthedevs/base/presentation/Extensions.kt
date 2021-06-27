package com.kryzl.meetthedevs.base.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.kryzl.meetthedevs.image.RemoteImageHandler

fun ImageView.setImageFromUrl(imageHandler: RemoteImageHandler, url: String, signature: String? = "default_signature") {
    if (url.isNotEmpty()) {
        imageHandler.setImageFromUrl(this, url, signature!!)
    } else {
        this.visibility = View.INVISIBLE
    }
}

inline fun <T : BaseFragment<*, *>> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
    return this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
}
