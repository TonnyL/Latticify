package io.github.tonnyl.latticify.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.InputStream

/**
 * Created by lizhaotailang on 23/09/2017.
 */

@GlideModule
class LatticifyAppGlideModule : AppGlideModule() {

    companion object {
        // Max cache size of glide.
        const val MAX_CACHE_SIZE = (1024 * 1024 * 512).toLong() // 512M

        // The cache directory name.
        const val CACHE_FILE_NAME = "IMG_CACHE" // cache file dir name
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        // 36MB, memory cache size
        // default value: 24MB
        val memoryCacheSize = (1024 * 1024 * 36).toLong()
        builder.setMemoryCache(LruResourceCache(memoryCacheSize))

        // Internal cache
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE))

        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        registry.replace(String::class.java, InputStream::class.java, HeaderedLoader.Factory())
    }

    /**
     * Disable the parsing of manifest file.
     */
    override fun isManifestParsingEnabled(): Boolean = false

}