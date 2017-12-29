package io.github.tonnyl.latticify.glide

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by lizhaotailang on 23/09/2017.
 */
object GlideLoader {

    fun loadAvatar(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .circleCrop()
                .into(imageView)
    }

    /**
     * Load the avatar of the logged-in user(On the home screen).
     *
     * @param textView Load the avatar image to the [textView]'s left drawable.
     * @param url The url of image.
     */
    fun loadAvatar(textView: TextView, url: String?) {
        GlideApp.with(textView.context)
                .asBitmap()
                .load(url)
                .circleCrop()
                .into(object : SimpleTarget<Bitmap>(96, 96) {
                    override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                        textView.setCompoundDrawablesWithIntrinsicBounds(BitmapDrawable(textView.context.resources, resource), null, null, null)
                    }
                })
    }

    fun loadNormal(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
    }

    fun loadWithPalette(imageView: ImageView, url: String?, callback: OnPaletteProcessedCallback) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .priority(Priority.HIGH)
                .into(object : BitmapImageViewTarget(imageView) {
                    // The function [onResourceReady] will called twice during one loading process.
                    override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                        super.onResourceReady(resource, transition)
                        resource?.let {
                            // The maximum color count is higher, the time of palette processing is more.
                            Palette.from(it).maximumColorCount(4).generate { palette ->
                                callback.onPaletteGenerated(palette)
                            }
                        } ?: run {
                            callback.onPaletteNotAvailable()
                        }
                    }
                })
    }

}