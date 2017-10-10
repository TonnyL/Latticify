package io.github.tonnyl.latticify

import android.app.Application
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.util.AccessTokenManager

/**
 * Created by lizhaotailang on 19/09/2017.
 */
class LatticifyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AccessTokenManager.init(this@LatticifyApp)

        RetrofitClient.init(this@LatticifyApp)
    }

}