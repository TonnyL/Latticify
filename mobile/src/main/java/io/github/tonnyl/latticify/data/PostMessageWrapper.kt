package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 07/10/2017.
 *
 * {
 * "ok": true,
 * "channel": "C4XA89BEJ",
 * "ts": "1507379063.000065",
 * "message": {
 * "text": "test",
 * "username": "Slack API Tester",
 * "bot_id": "B78PP0XB8",
 * "type": "message",
 * "subtype": "bot_message",
 * "ts": "1507379063.000065"
 * }
 * }
 */
data class PostMessageWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("ts")
        val ts: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("message")
        val message: Message

)