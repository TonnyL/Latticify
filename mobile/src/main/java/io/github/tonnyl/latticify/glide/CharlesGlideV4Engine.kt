package io.github.tonnyl.latticify.glide

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import io.github.tonnyl.charles.engine.ImageEngine

class CharlesGlideV4Engine : ImageEngine {

    override fun loadImage(context: Context, imageView: ImageView, uri: Uri) {
        GlideLoader.loadCharlesThumbnail(imageView, uri.toString())
    }

}