package io.github.tonnyl.latticify.util

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import io.github.tonnyl.latticify.data.AccessToken

/**
 * Created by lizhaotailang on 24/09/2017.
 */
object AccessTokenManager {

    private lateinit var accessToken: AccessToken

    val KEY_MOCK_ACCESS_TOKEN = "KEY_MOCK_ACCESS_TOKEN"

    fun init(context: Context) {
        accessToken = Gson().fromJson<AccessToken>(PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_MOCK_ACCESS_TOKEN, ""), AccessToken::class.java)
    }

    fun getAccessToken(): AccessToken = accessToken

    fun setAccessToken(context: Context, accessToken: AccessToken) {
        this.accessToken = accessToken
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_MOCK_ACCESS_TOKEN, Gson().toJson(accessToken, AccessToken::class.java))
                .apply()
    }

}