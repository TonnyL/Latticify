package io.github.tonnyl.latticify.retrofit

import io.github.tonnyl.latticify.BuildConfig

/**
 * Created by lizhaotailang on 19/09/2017.
 */
class Api private constructor() {

    companion object {
        val CLIENT_ID = BuildConfig.CLIENT_ID
        val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        val VERIFICATION_TOKEN = BuildConfig.VERIFICATION_TOKEN

        val SLACK_API_BASE_URL = "https://slack.com/api/"
        val SLACK_AUTHORIZE_URL = "https://slack.com/oauth/authorize"

        val SLACK_AUTHORIZE_CALLBACK_URI = "https://tonnyl.github.io/callback"
        val SLACK_AUTHORIZE_CALLBACK_URI_HOST = "tonnyl.github.io"
        val SLACK_AUTHORIZE_SCOPE = "identify+read+post+client+admin"

        val LIMIT = 20
    }

}