package com.kryzl.meetthedevs.image

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException

class GlideImageHandler : RemoteImageHandler {

    override fun setImageFromUrl(imageView: ImageView, url: String, signature: String) {
        val requestOptions = RequestOptions().fitCenter().signature(ObjectKey(signature))
        Glide.with(imageView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(url)
            .into(imageView)
    }

    override fun getBitmapFromUrl(
        context: Context,
        url: String,
        signature: String
    ): Bitmap? {
        val requestOptions = RequestOptions().fitCenter().signature(ObjectKey(signature))
        return try {
            Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(url)
                .submit()
                .get()
        } catch (exception: ExecutionException) {
            Log.i(TAG, "Execution exception encountered while getting image", exception)
            null
        } catch (exception: InterruptedException) {
            Log.i(TAG, "Interrupted exception encountered while getting image", exception)
            null
        } catch (exception: CancellationException) {
            Log.i(TAG, "Cancellation exception encountered while getting image", exception)
            null
        }
    }

    companion object {
        private const val TAG = "GlideImageHandler"
    }

}
