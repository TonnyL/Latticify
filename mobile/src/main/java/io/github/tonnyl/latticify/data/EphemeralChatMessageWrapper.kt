package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 * {
 * "ok": true,
 * "message_ts": "1502210682.580145"
 * }
 */
data class EphemeralChatMessageWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("message_ts")
        val messageTs: String

)