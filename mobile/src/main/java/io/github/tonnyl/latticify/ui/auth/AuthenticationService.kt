package io.github.tonnyl.latticify.ui.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by lizhaotailang on 02/01/2018.
 */
class AuthenticationService : Service() {

    private lateinit var mAuthenticator: Authenticator

    override fun onCreate() {
        super.onCreate()

        mAuthenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder = mAuthenticator.iBinder

}