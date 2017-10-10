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
        val SLACK_AUTHORIZE_SCOPE = "admin+commands" + // others
                "+channels:history+channels:read+channels:write" + // channels
                "+chat:write:bot+chat:write:user" + // chat
                "+dnd:read+dnd:write" + // dnd
                "+emoji:read" + // emoji
                "+files:read+files:write:user" + // files
                "+groups:history+groups:read+groups:write" + // groups
                "+im:history+im:read+im:write" + // im
                "+links:read+links:write" + // links
                "+mpim:history+mpim:read+mpim:write" + // mpim
                "+pins:read+pins:write" + // pins
                "+reactions:read+reactions:write" + // reactions
                "+reminders:read+reminders:write" + // reminders
                "+search:read" + // search
                "+stars:read+stars:write" + // stars
                "+team:read" + // team
                "+usergroups:read+usergroups:write" + // user groups
                "+users.profile:read+users.profile:write" + // user's profile
                "+users:read+users:read.email+users:write" // users

        val LIMIT = 20
    }

}