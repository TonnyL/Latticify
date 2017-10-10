package io.github.tonnyl.latticify.retrofit

import android.content.Context
import io.github.tonnyl.latticify.BuildConfig
import io.github.tonnyl.latticify.data.AccessToken
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

    fun init(context: Context) {
        mCache?.let {
            throw IllegalStateException("Retrofit cache already initialized.")
        }
        mCache = Cache(context.cacheDir, 20 * 1024 * 1024)
    }

    fun <T> createService(serviceClass: Class<T>, accessToken: AccessToken?): T {

        if (mRetrofit == null) {
            // Custom the http client.
            val httpClientBuilder = OkHttpClient.Builder()

            httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })

            httpClientBuilder.addInterceptor { chain ->
                val original = chain.request()
                // Custom the request header.
                val requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .apply {
                            accessToken?.accessToken?.let {
                                header("Authorization", "Bearer $it")
                                header("token", it)
                            }
                        }
                        .method(original.method(), original.body())
                val request = requestBuilder.build()

                chain.proceed(request)
            }.cache(mCache)

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