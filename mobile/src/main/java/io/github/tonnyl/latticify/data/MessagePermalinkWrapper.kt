package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * {
 * "ok": true,
 * "channel": "C1H9RESGA",
 * "permalink": "https:\/\/ghostbusters.slack.com\/archives\/C1H9RESGL\/p135854651700023?thread_ts=1358546515.000008&cid=C1H9RESGL"
 * }
 *
 * {
 * "ok": false,
 * "error": "channel_not_found"
 * }
 */

data class MessagePermalinkWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("channel")
        val channel: String?,

        @SerializedName("permalink")
        val permalink: String?,

        @SerializedName("error")
        val error: String?

)