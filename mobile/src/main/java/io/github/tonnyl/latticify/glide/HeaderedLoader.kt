package io.github.tonnyl.latticify.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import java.io.InputStream

class HeaderedLoader : BaseGlideUrlLoader<String> {

    private constructor(concreteLoader: ModelLoader<GlideUrl, InputStream>) : super(concreteLoader)

    private constructor(concreteLoader: ModelLoader<GlideUrl, InputStream>, modelCache: ModelCache<String, GlideUrl>?) : super(concreteLoader, modelCache)

    override fun getUrl(s: String, width: Int, height: Int, options: Options): String = s

    override fun handles(s: String): Boolean = true

    override fun getHeaders(s: String?, width: Int, height: Int, options: Options?): Headers? {
        return LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer ${RetrofitClient.mToken}")
                .build()
    }

    class Factory : ModelLoaderFactory<String, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> = HeaderedLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java))

        override fun teardown() {

        }
    }


}
