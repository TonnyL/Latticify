package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 * {
 * "ok": true,
 * "channel": "C024BE91L",
 * "ts": "1401383885.000061"
 * }
 */
data class ChatMessageWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("ts")
        val ts: String,

        @SerializedName("text")
        val text: String?
)