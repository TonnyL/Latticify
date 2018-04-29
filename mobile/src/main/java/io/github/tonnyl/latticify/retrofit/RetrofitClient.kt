package io.github.tonnyl.latticify.retrofit

import android.content.Context
import io.github.tonnyl.latticify.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by lizhaotailang on 23/09/2017.
 */
object RetrofitClient {

    // The [retrofit2.Retrofit] instance for whole app.
    private var mRetrofit: Retrofit? = null

    private var mCache: Cache? = null

    var mToken = Api.VERIFICATION_TOKEN

    fun init(context: Context) {
        mCache?.let {
            throw IllegalStateException("Retrofit cache already initialized.")
        }
        mCache = Cache(context.cacheDir, 20 * 1024 * 1024)
    }

    fun <T> createService(serviceClass: Class<T>): T {

        if (mRetrofit == null) {
            // Custom the http client.
            val httpClientBuilder = OkHttpClient.Builder()

            httpClientBuilder.addInterceptor { chain ->
                val original = chain.request()
                // Custom the request header.
                val requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .method(original.method(), original.body())
                val request = requestBuilder.build()

                chain.proceed(request)
            }.cache(mCache)

            if (BuildConfig.DEBUG) {
                httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            }

            httpClientBuilder.retryOnConnectionFailure(true)

            // Set the corresponding convert factory and call adapter factory.
            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(Api.SLACK_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            mRetrofit = retrofitBuilder
                    .client(httpClientBuilder.build())
                    .build()
        }

        return mRetrofit!!.create(serviceClass)
    }

}