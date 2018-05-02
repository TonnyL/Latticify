package io.github.tonnyl.latticify.glide

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.retrofit.RetrofitClient

/**
 * Created by lizhaotailang on 23/09/2017.
 */
object GlideLoader {

    fun loadAvatar(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .placeholder(R.drawable.bg_avatar)
                .circleCrop()
                .into(imageView)
    }

    fun loadNormal(imageView: ImageView, url: String?) {
        val headers = LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer ${RetrofitClient.mToken}")
                .build()

        GlideApp.with(imageView.context)
                .asBitmap()
                .load(GlideUrl(url, headers))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
    }

    fun loadCharlesThumbnail(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    fun loadMatissePreview(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .fitCenter()
                .into(imageView)
    }

    fun loadMatisseThumbnail(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(imageView)
    }

    fun loadMatisseThumbnailAsGif(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asGif()
                .load(url)
                .centerCrop()
                .into(imageView)
    }

    fun loadMatissePreviewAsGif(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asGif()
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .fitCenter()
                .into(imageView)
    }

    fun loadWithPalette(imageView: ImageView, url: String?, callback: OnPaletteProcessedCallback) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .priority(Priority.HIGH)
                .into(object : BitmapImageViewTarget(imageView) {

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        super.onResourceReady(resource, transition)
                        // The maximum color count is higher, the time of palette processing is more.
                        Palette.from(resource)
                                .maximumColorCount(4)
                                .generate { palette ->
                                    callback.onPaletteGenerated(palette)
                                }
                    }

                })
    }

}