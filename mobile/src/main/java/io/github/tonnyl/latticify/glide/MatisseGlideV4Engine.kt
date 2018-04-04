package io.github.tonnyl.latticify.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.zhihu.matisse.engine.ImageEngine

class MatisseGlideV4Engine : ImageEngine {

    override fun loadAnimatedGifThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?) {
        imageView?.let {
            GlideLoader.loadNormal(it, uri.toString())
        }
    }

    override fun loadImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?) {
        imageView?.let {
            GlideLoader.loadNormal(it, uri.toString())
        }
    }

    override fun loadAnimatedGifImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?) {
        imageView?.let {
            GlideLoader.loadNormal(it, uri.toString())
        }
    }

    override fun supportAnimatedGif(): Boolean = true

    override fun loadThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?) {
        imageView?.let {
            GlideLoader.loadNormal(it, uri.toString())
        }
    }

}